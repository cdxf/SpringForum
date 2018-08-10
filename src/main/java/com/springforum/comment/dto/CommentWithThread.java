package com.springforum.comment.dto;

import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
public class CommentWithThread {
    public Integer id;
    public Instant updatedTime;
    public Instant createdTime;
    public ThreadDTO thread;
    private String content;
    private UserSummary author;
}
