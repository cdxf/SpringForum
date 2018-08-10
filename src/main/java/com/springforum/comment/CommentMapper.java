package com.springforum.comment;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.thread.ThreadMaper;
import com.springforum.user.UserMapper;

public abstract class CommentMapper {
//    public static SimpleComment commentToSimpleComment(Comment value) {
//        if (value == null) return null;
//        return SimpleComment.builder()
//                .id(value.getId())
//                .createdTime(value.getCreatedTime())
//                .updatedTime(value.getUpdatedTime())
//                .author(UserMapper.userToUserSummary(value.getAuthor()))
//                .content(value.getContent())
//                .build();
//    }

    public static CommentWithThread commentToCommentWithThread(Comment value) {
        if (value == null) return null;
        return CommentWithThread.builder()
                .author(UserMapper.userToUserSummary(value.getAuthor()))
                .content(value.getContent())
                .thread(ThreadMaper.threadDTOMapper(value.getThread()))
                .build();
    }
}