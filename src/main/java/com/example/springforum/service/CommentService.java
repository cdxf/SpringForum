package com.example.springforum.service;

import com.example.springforum.entity.Comment;
import com.example.springforum.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Transactional
    public Comment newComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
