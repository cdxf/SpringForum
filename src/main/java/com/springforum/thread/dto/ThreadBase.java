package com.springforum.thread.dto;

import com.springforum.comment.dto.SimpleComment;
import com.springforum.forum.dto.ForumBase;
import com.springforum.generic.BaseEntity;
import com.springforum.user.dto.UserSummary;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ThreadBase extends BaseEntity {

    public String title;

    public UserSummary author;

    public ForumBase forum;

    public Instant lastModified;

    public Integer comments;

    public Integer views;

    public SimpleComment lastReply;

    @Builder
    public ThreadBase(Integer id, Instant createdTime, Instant updatedTime, Instant lastModified,
                      String title,
                      Integer authorId, String authorName, String authorAvatar,
                      Integer forumId, String forumName,
                      Integer comments, Integer views,
                      Integer lastReplyId, Instant lastReplyLastUpdated,
                      Integer lastReplyAuthorId, String lastReplyAuthorName, String lastReplyAuthorAvatar) {
        super(id, createdTime, updatedTime);
        this.lastModified = lastModified;
        this.lastReply = SimpleComment.builder()
                .id(lastReplyId)
                .updatedTime(lastReplyLastUpdated)
                .author(UserSummary
                        .builder()
                        .id(lastReplyAuthorId)
                        .username(lastReplyAuthorName)
                        .avatar(lastReplyAuthorAvatar)
                        .build())
                .build();
        this.title = title;
        this.author = UserSummary.builder().id(authorId).username(authorName).avatar(authorAvatar).build();
        this.forum = ForumBase.builder().id(forumId).name(forumName).build();
        this.comments = comments;
        this.views = views;
    }
}
