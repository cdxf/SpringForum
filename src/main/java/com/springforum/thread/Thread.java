package com.springforum.thread;

import com.springforum.comment.Comment;
import com.springforum.forum.Forum;
import com.springforum.generic.BaseEntity;
import com.springforum.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(indexes = {
        @Index(name = "lastModified", columnList = "last_modified"),
        @Index(name = "thread_author_id", columnList = "author_id"),
        @Index(name = "thread_forum_id", columnList = "forum_id"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Thread extends BaseEntity {
    @NotBlank
    private String title;
    @Column(length = 10485760)
    @NotBlank
    @Length(min = 20)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "forum_id")
    @NotNull
    private Forum forum;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    @NotNull
    private User author;
    private Integer comments;
    private Integer views;
    @Column(name = "last_modified")
    private Instant lastModified;
    @OneToOne(fetch = LAZY)
    private Comment lastReply;

    public Thread(String title, String content, @NotNull Forum forum, @NotNull User author) {
        this.title = title;
        this.content = content;
        this.forum = forum;
        this.author = author;
        this.comments = 0;
        this.views = 0;
        this.lastModified = Instant.now();
    }

    @PrePersist
    public void updateLatestThread() {
        Forum nextForum = this.forum;
        while (nextForum != null) {
            nextForum.setLatestThread(this);
            nextForum = nextForum.getParent();
        }
    }
}
