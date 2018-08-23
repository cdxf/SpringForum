package com.springforum.comment;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.generic.TimestampKeyset;
import com.springforum.user.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@Getter
@Setter
public class CommentAPI {
    @Autowired private CommentService commentService;
    @Autowired private UserService userService;

    /**
     * Get a specific comment by its ID
     *
     * @param commentID
     * @return
     */
    @GetMapping("comments/{commentID}")
    public Optional<CommentWithThread> getComment(@PathVariable Integer commentID) {
        return commentService.getComment(commentID);
    }

    @GetMapping("/comments")
    public List<CommentWithThread> getLatestComment(TimestampKeyset keyset) {
        return commentService.getCommentDAO().getLatestComments(keyset);
    }
}
