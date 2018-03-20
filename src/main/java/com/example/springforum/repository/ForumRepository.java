package com.example.springforum.repository;

import com.example.springforum.entity.Forum;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends PagingAndSortingRepository<Forum,Integer> {
        Iterable<Forum> findAllByParent(Forum forum);
}
