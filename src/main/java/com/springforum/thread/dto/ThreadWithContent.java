package com.springforum.thread.dto;

import com.springforum.forum.dto.ForumBase;
import com.springforum.generic.BaseEntity;
import com.springforum.user.dto.UserSummary;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ThreadWithContent extends BaseEntity {
    public String title;
    public Instant lastModified;
    public UserSummary author;
    public Integer comments;
    public Integer views;
    public String content;
    public ForumBase forum;

    @Builder
    public ThreadWithContent(Integer id, Instant createdTime, Instant updatedTime, String title, Instant lastModified,
                             Integer authorId, String authorName, String authorAvatar,
                             Integer comments, Integer views, String content,
                             Integer forumId) {
        super(id, createdTime, updatedTime);
        this.title = title;
        this.lastModified = lastModified;
        this.forum = ForumBase.builder().id(forumId).build();
        this.author = UserSummary.builder()
                .id(authorId)
                .username(authorName)
                .avatar(authorAvatar)
                .build();
        this.comments = comments;
        this.views = views;
        this.content = content;
    }
}
