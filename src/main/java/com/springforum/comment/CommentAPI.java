package com.springforum.comment;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.comment.dto.NewCommentProjection;
import com.springforum.comment.dto.SimpleComment;
import com.springforum.generic.Benchmark;
import com.springforum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentAPI {
    final private CommentService commentService;
    final private UserService userService;

    @Autowired
    public CommentAPI(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping(value = "/", params = {"threadID"})
    public Page<SimpleComment> getCommentsFromThread(@RequestParam("threadID") Integer threadID, Pageable pageable) {
        return Benchmark.call(() -> commentService.getCommentsFromThread(threadID, pageable));
    }

    @GetMapping("/{commentID}")
    public Optional<CommentWithThread> getComment(@PathVariable Integer commentID) {
        System.out.println("WTF22FF");
        return commentService.getComment(commentID);
    }

    @PostMapping("/")
    @Secured("ROLE_USER")
    public Optional<CommentWithThread> postComment(NewCommentProjection payload) {
        return commentService
                .postComment(
                        payload.getThreadId(),
                        payload.getContent(),
                        userService.getCurrentUser().getId()
                );
    }

}
