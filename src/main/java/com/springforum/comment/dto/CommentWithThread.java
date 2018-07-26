package com.springforum.comment.dto;

import com.springforum.forum.dto.IdOnly;
import com.springforum.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentWithThread extends BaseEntity {
    public IdOnly thread;
    private String content;
    private IdOnly author;

    @Builder
    public CommentWithThread(Integer id, Instant createdTime, Instant updatedTime, String content, Integer author, Integer thread) {
        super(id, createdTime, updatedTime);
        this.content = content;
        this.author = new IdOnly(author);
        this.thread = new IdOnly(thread);
    }
}
