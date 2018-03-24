package com.example.springforum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends BaseEntity{
    @Getter
    @Setter
    public String content;
    @ManyToOne(optional = false)
    @Getter
    @Setter
    public Thread thread;
    @Getter
    @Setter
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
