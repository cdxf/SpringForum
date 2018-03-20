package com.example.springforum.repository;

import com.example.springforum.entity.Thread;
import com.example.springforum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ThreadRepository extends PagingAndSortingRepository<Thread,Integer> {
    Thread findByTitle(String title);
    Iterable<Thread> findThreadsByAuthor(User user);
    Page<Thread> findThreadsByForum_Id(Integer forumID, Pageable pageable);
    Thread findThreadByForumIdOrderByUpdatedTime(Integer ForumID);
    Long countDistinctByForumId(Integer id);
}
