package com.example.springforum.service;

import com.example.springforum.entity.Forum;
import com.example.springforum.entity.SiteDetail;
import com.example.springforum.model.ForumSummary;
import com.example.springforum.model.SubForumInfo;
import com.example.springforum.repository.ForumRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ForumService {
    @Autowired  ForumRepository forumRepository;
    @Autowired  ThreadService threadService;
    @Autowired
    private SiteDetailService siteDetailService;

    @Transactional
    public Forum add(String name, String description, Integer parentID) {
        val parent = Objects.isNull(parentID) ? null : forumRepository.findById(parentID).orElse(null);
        val forum = new Forum(name, description, parent);
        return forumRepository.save(forum);
    }

    @Cacheable(cacheNames = "forum",key = "#forumID")
    public Optional<Forum> getForum(Integer forumID) {
        return forumRepository.findById(forumID);
    }

    public ForumSummary getRootForumSummary(){
        SiteDetail name = siteDetailService.getKey("sitename").orElseThrow(IllegalArgumentException::new);
        Forum forum = new Forum(name.getValue(),"",null);
        return ForumSummary.builder()
                .id(null)
                .name(forum.getName())
                .description(forum.getDescription())
                .path(null)
                .subforums(getSubForumInfo())
                .threads(Set.of())
                .build();
    }
    public ForumSummary getForumSummary(Integer forumID){
        Forum forum = getForum(forumID).orElse(new Forum());
        Iterable<SubForumInfo> subForumInfos = getSubForumInfo(forum);
        ForumSummary forumSummary = ForumSummary.builder()
                .id(forum.getId())
                .name(forum.getName())
                .description(forum.getDescription())
                .path(getPath(forum))
                .subforums(getSubForumInfo(forum))
                .threads(threadService.findNewestThread(PageRequest.of(0,10),forumID))
                .build();
        return forumSummary;
    }
    public Iterable<SubForumInfo> buildSubForumSummaries(Iterable<Forum> subforums){
        List<SubForumInfo> subForumInfoList = new LinkedList<>();
        subforums.forEach(subforum->{
            val lastThread = threadService.getLastThread(subforum.getId());
            val threadCount = threadService.getThreadCount(subforum.getId());

            val subForumInfo =SubForumInfo.builder()
                .id(subforum.getId())
                .name(subforum.getName())
                .description(subforum.getDescription())
                .lastThread(lastThread)
                .threadCount(threadCount)
                .build();
        subForumInfoList.add(subForumInfo);
    });
        return subForumInfoList;
    }
    public Iterable<SubForumInfo> getSubForumInfo(){
        Iterable<Forum> subforums = getRootSubForum();
        return buildSubForumSummaries(subforums);
    }
    public Iterable<SubForumInfo> getSubForumInfo(Forum forum){
        Iterable<Forum> subforums= getSubForum(forum);
        return buildSubForumSummaries(subforums);
    }
    @Cacheable(cacheNames = "subForum",key = "#forum.id")
    public Iterable<Forum> getSubForum(Forum forum) {
        return Optional.ofNullable(forum)
                .map(it -> (Iterable<Forum>)it.subforums)
                .orElse(Set.of());
    }
    @Cacheable(cacheNames = "forumPath",key = "#forum.id")
    public Iterable<Forum> getPath(Forum forum){
        if(forum == null) throw new IllegalArgumentException();
        List<Forum> paths= new ArrayList<>();
        paths.add(forum);
        while((forum = forum.getParent())!= null){
            paths.add(forum);
        }
        Collections.reverse(paths);
        return paths;
    }

    public Iterable<Forum> getRootSubForum() {
        return forumRepository.findAllByParent(null);
    }

}
