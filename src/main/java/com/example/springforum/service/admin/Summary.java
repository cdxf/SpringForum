package com.example.springforum.service.admin;

import com.example.springforum.entity.Forum;
import com.example.springforum.entity.User;

import java.util.List;

public class Summary {
    Iterable<User> users;

    public Iterable<User> getUsers() {
        return users;
    }

    public void setUsers(Iterable<User> users) {
        this.users = users;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    List<Forum> forums;
}
