package com.example.springforum.entity;

import javax.persistence.*;

@Entity
public class Comment extends BaseEntity{
    String content;
    @ManyToOne(optional = false)
    public Thread thread;
    @ManyToOne(optional = false)
    public User author;

    public Comment() {
    }

    public Comment(String content, Thread thread, User author) {
        this.content = content;
        this.thread = thread;
        this.author = author;
    }
}
