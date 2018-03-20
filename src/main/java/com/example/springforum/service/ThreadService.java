package com.example.springforum.service;

import com.example.springforum.entity.Thread;
import com.example.springforum.entity.User;
import com.example.springforum.exception.ForumNotExistException;
import com.example.springforum.exception.UserNotExistException;
import com.example.springforum.repository.ThreadRepository;
import com.example.springforum.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ThreadService {
    @Autowired private ThreadRepository threadRepository;
    @Autowired private ForumService forumService;
    @Autowired private UserRepository userRepository;
    @Transactional
    public Thread create(String title,String content,Integer forumID, User user){
        val forum = forumService.getForum(forumID).orElseThrow(ForumNotExistException::new);
        Thread thread = new Thread(title,content,forum,user);
        threadRepository.save(thread);
        return thread;
    }
    public Iterable<Thread> findNewestThread(Pageable page, Integer forumID){
        return threadRepository.findThreadsByForum_Id(forumID, page);
    }
    public Thread getLastThread(Integer forumID){
        return threadRepository.findThreadByForumIdOrderByUpdatedTime(forumID);
    }
    @Cacheable(cacheNames = "threadCount",key = "#forumID")
    public Long getThreadCount(Integer forumID){ return threadRepository.countDistinctByForumId(forumID);}
    public Iterable<Thread> findThreadsByAuthor(String authorName){
        User author = userRepository.getByUsername(authorName).orElseThrow(() -> new UserNotExistException(authorName));
        return threadRepository.findThreadsByAuthor(author);
    }
}
