package com.springforum.comment;

import com.springforum.generic.BaseEntity;
import com.springforum.thread.Thread;
import com.springforum.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(indexes = {
        @Index(name = "comment_thread_id", columnList = "thread_id"),
        @Index(name = "comment_author_id", columnList = "author_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @NotBlank
    @Column(length = 10485760)
    private String content;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Thread thread;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
}
