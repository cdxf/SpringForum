package com.springforum.forum;

import com.springforum.forum.dto.ForumBase;
import com.springforum.generic.PathItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
public class ForumService {
    final ForumRepository forumRepository;
    @Autowired
    EntityManager entityManager;

    @Autowired
    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Transactional
    public Optional<Forum> add(@NotNull String name, String description, Integer parentID) {
        var parent = parentID == null ? null : entityManager.getReference(Forum.class, parentID);
        var forum = new Forum(name, description, parent);
        return Optional.ofNullable(forumRepository.save(forum));
    }

    public Iterable<ForumBase> getForums() {
        return forumRepository.getAll();
    }

    private Optional<Forum> getForum(Integer forumID) {
        return forumRepository.findById(forumID);
    }

    public ForumBase getForumDTO(Integer forumID) {
        return forumRepository.getById(forumID);
    }

    private PathItemDTO buildPathItem(Forum forum) {
        return PathItemDTO.builder()
                .id(forum.getId())
                .name(forum.getName())
                .type(PathItemDTO.Type.FORUM)
                .build();
    }

    @Cacheable(cacheNames = "forum_path", key = "#forumID")
    public Iterable<PathItemDTO> getPath(Integer forumId) {
        if (forumId == null) return null;
        Forum forum = getForum(forumId).orElseThrow(IllegalArgumentException::new);
        List<PathItemDTO> paths = new ArrayList<>();
        paths.add(buildPathItem(forum));
        while ((forum = forum.getParent()) != null) {
            paths.add(buildPathItem(forum));
        }
        Collections.reverse(paths);
        return paths;
    }

    public Iterable<ForumBase> getRootForum() {
        var root = forumRepository.findAllRoot();
        return StreamSupport
                .stream(root.spliterator(), false)
                .map(it -> ForumMaper.forumToForumBase(it))
                .collect(Collectors.toList());
    }
}
