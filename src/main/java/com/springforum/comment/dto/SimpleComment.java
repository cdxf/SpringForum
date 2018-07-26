package com.springforum.comment.dto;

import com.springforum.generic.BaseEntity;
import com.springforum.user.dto.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class SimpleComment extends BaseEntity {
    private String content;
    private UserSummary author;

    @Builder
    public SimpleComment(Integer id, Instant createdTime, Instant updatedTime, String content, UserSummary author) {
        super(id, createdTime, updatedTime);
        this.content = content;
        this.author = author;
    }

    public SimpleComment(
            Integer id,
            Instant createdTime,
            Instant updatedTime,
            String content,
            Integer authorId,
            String authorUserName,
            String authorAvatar) {
        super(id, createdTime, updatedTime);
        this.content = content;
        this.author = UserSummary.builder().id(authorId)
                .username(authorUserName)
                .avatar(authorAvatar)
                .build();
    }
}
