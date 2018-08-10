package com.springforum.comment.create_comment;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.comment.dto.NewCommentProjection;
import com.springforum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CreateCommentController {
    @Autowired CreateCommentService createCommentService;
    @Autowired UserService userService;

    @PostMapping("api/comments")
    @Secured("ROLE_USER")
    public Optional<CommentWithThread> createComment(@Valid @RequestBody NewCommentProjection payload) {
        return createCommentService
                .createComment(
                        payload.getThreadId(),
                        payload.getContent(),
                        userService.getCurrentUser().getId()
                );
    }
}
