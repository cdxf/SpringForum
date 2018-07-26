package com.springforum.comment;

import com.springforum.generic.BaseEntity;
import com.springforum.thread.Thread;
import com.springforum.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(indexes = {
        @Index(name = "comment_thread_id", columnList = "thread_id"),
        @Index(name = "comment_author_id", columnList = "author_id")
})
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @NotBlank
    @Lob
    private String content;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Thread thread;
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;
}
