package com.example.springforum.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Thread extends BaseEntity{
    @Getter @Setter
    @NotBlank
    public String title;
    @Getter @Setter
    @NotNull
    public String content;

    @OneToMany(mappedBy = "thread",cascade = CascadeType.ALL)
    public Set<Comment> comments = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    public Forum forum;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @NotNull
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
