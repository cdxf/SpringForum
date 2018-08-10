package com.springforum.thread.create_new_thread;

import com.springforum.thread.dto.NewThreadCommand;
import com.springforum.thread.dto.ThreadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CreateThreadController {
    @Autowired CreateThreadService createThreadService;

    @PostMapping("/api/threads")
    @Secured("ROLE_USER")
    public ThreadDTO newThread(@Valid @RequestBody NewThreadCommand thread) {
        return createThreadService.newThread(thread.getTitle(), thread.getContent(), thread.getForumId());
    }
}
