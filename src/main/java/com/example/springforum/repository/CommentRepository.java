package com.example.springforum.repository;

import com.example.springforum.entity.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment,Integer> {
        List<Comment> getCommentsByThread(Thread thread);
}
