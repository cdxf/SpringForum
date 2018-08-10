package com.springforum.thread.dto;

import com.springforum.comment.dto.SimpleComment;
import com.springforum.forum.dto.ForumDTO;
import com.springforum.user.dto.UserSummary;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreadDTO {

    private Integer id;

    private Instant createdTime;

    private Instant updatedTime;

    private Instant lastModified;

    private String title;

    private UserSummary author;

    private ForumDTO forum;

    private Integer comments;

    private Integer views;

    private String summary;

    private String content;

    private SimpleComment lastReply;

}
