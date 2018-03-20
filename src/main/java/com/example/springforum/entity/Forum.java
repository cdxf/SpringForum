package com.example.springforum.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Forum extends BaseEntity{
    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public Set<Forum> subforums = new HashSet<>();
    @OneToMany(mappedBy = "forum")
    public Set<Thread> thread = new HashSet<>();
    @Getter @Setter
    @NotBlank
    String name;
    @Getter @Setter
    String description;
    @ManyToOne(optional = true)
    @Getter @Setter
    Forum parent;

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

    public void addChild(Forum child){
        subforums.add(child);
        child.parent = this;
    }

    @Override
    public String toString() {
        String s = name + ":" + description + ':' + Optional.ofNullable(parent).map(i->i.name).orElse("");
        return s;
    }
}
