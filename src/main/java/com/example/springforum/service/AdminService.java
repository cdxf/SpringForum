package com.example.springforum.service;

import com.example.springforum.service.admin.Summary;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminService {

    @Autowired
    UserService userService;
    public Summary getSummary(){
        Summary summary = new Summary();
        summary.setUsers(userService.getTopUsers());
        return new Summary();

    }
}
