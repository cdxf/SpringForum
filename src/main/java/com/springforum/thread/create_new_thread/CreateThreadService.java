package com.springforum.thread.create_new_thread;

import com.springforum.forum.Forum;
import com.springforum.thread.Thread;
import com.springforum.thread.ThreadMaper;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.User;
import com.springforum.user.UserService;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreateThreadService {
    @Autowired private EntityManager entityManager;
    @Autowired private PolicyFactory sanitizer;
    @Autowired private UserService userService;

    public ThreadDTO newThread(String title, String content, Integer forumID) {
        return newThread(title, content, forumID, userService.getCurrentUser().getId());
    }

    public ThreadDTO newThread(String title, String content, Integer forumID, Integer userId) {
        Assert.notNull(forumID, "forumID must not be null");
        Assert.notNull(userId, "userId must not be null");
        content = sanitizer.sanitize(content);
        title = sanitizer.sanitize(title);
        User user = entityManager.find(User.class, userId);
        Forum forum = entityManager.find(Forum.class, forumID);
        var thread = new Thread(title, content, forum, user);
        entityManager.persist(thread);
        while (forum != null) {
            forum.setThreads(forum.getThreads() + 1);
            forum.setPosts(forum.getPosts() + 1);
            forum.setLatestThread(thread);
            forum = forum.getParent();
        }
        user.setThreads(user.getThreads() + 1);
        return ThreadMaper.threadDTOMapper(thread);
    }


    public List<ThreadDTO> newMultipleThread(List<String> titles, List<String> contents, Integer forumID, List<Integer> userIds) {
        var result = new ArrayList<Thread>();
        Forum forum = entityManager.find(Forum.class, forumID);
        for (int i = 0; i < titles.size(); i++) {
            User user = entityManager.find(User.class, userIds.get(i));
            var thread = new Thread(titles.get(i), contents.get(i), forum, user);
            user.setThreads(user.getThreads() + 1);
            entityManager.persist(thread);
            result.add(thread);
        }
        while (forum != null) {
            forum.setThreads(forum.getThreads() + titles.size());
            forum.setPosts(forum.getPosts() + titles.size());
            forum.setLatestThread(result.get(result.size() - 1));
            forum = forum.getParent();
        }

        return result.stream().map(it -> ThreadMaper.threadDTOMapper(it)).collect(Collectors.toList());
    }
}
