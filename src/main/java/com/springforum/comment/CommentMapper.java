package com.springforum.comment;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.comment.dto.SimpleComment;
import com.springforum.user.UserMapper;
import org.springframework.stereotype.Component;

@Component
public abstract class CommentMapper {
    public static SimpleComment commentToSimpleComment(Comment value) {
        if (value == null) return null;
        return SimpleComment.builder()
                .id(value.getId())
                .createdTime(value.getCreatedTime())
                .updatedTime(value.getUpdatedTime())
                .author(UserMapper.userToUserSummary(value.getAuthor()))
                .content(value.getContent())
                .build();
    }

    public static CommentWithThread commentToCommentWithThread(Comment value) {
        if (value == null) return null;
        return CommentWithThread.builder()
                .author(value.getAuthor().getId())
                .content(value.getContent())
                .thread(value.getThread().getId())
                .build();
    }
}