package com.springforum.thread;

import com.springforum.generic.PathItemDTO;
import com.springforum.thread.dto.ThreadBase;
import com.springforum.thread.dto.ThreadWithContent;
import com.springforum.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ThreadService {
    ThreadWithContent newThread(String title, String content, Integer forumID, Integer userId);

    List<ThreadWithContent> newMultipleThread(List<String> titles, List<String> contents, Integer forumID, List<Integer> userIds);

    Optional<ThreadWithContent> getThread(Integer id);

    ThreadWithContent modifyThread(Integer threadID, String content, Integer userId);

    Iterable<ThreadWithContent> getThreadsByForum(Integer forumID, Pageable page);

    Iterable<ThreadBase> getThread(Set<Integer> ids);

    void increaseView(Integer threadId);

    Page<ThreadBase> getLastest(Pageable page);

    Iterable<ThreadWithContent> getThreadsByAuthor(User author);

    Iterable<PathItemDTO> getPath(Integer threadID);

}
