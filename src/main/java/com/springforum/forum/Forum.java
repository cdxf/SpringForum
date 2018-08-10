package com.springforum.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springforum.generic.BaseEntity;
import com.springforum.thread.Thread;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@Table(indexes = {
        @Index(name = "latestThreadIndex", columnList = "latest_thread")
})
public class Forum extends BaseEntity {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Forum parent;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "latest_thread")
    private Thread latestThread = null;
    private Integer threads = 0;
    private Integer posts = 0;

    public Forum() {
    }

    public Forum(String name, String description, Forum parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public Forum(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
