package com.example.springforum.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Thread extends BaseEntity{
    @Getter @Setter
    public String title;
    @Getter @Setter
    public String content;

    @OneToMany(mappedBy = "thread",cascade = CascadeType.ALL)
    public Set<Comment> comments = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    public Forum forum;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    public User author;
    public Thread() {
    }

    public Thread(String title, String content, Forum forum, User author) {
        this.title = title;
        this.content = content;
        this.forum = forum;
        this.author = author;
    }
}
