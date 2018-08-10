package com.springforum.thread;


import com.springforum.generic.TimestampKeyset;
import com.springforum.thread.dto.ThreadDTO;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Accessors
public class ThreadAPI {
    @Autowired
    private ThreadReadService threadReadService;
    @Autowired
    private ThreadWriteService threadWriteService;

    @GetMapping(value = "forums/{forum}/threads")
    public List<ThreadDTO> threads(@PathVariable("forum") Integer forum, TimestampKeyset keyset) {
        return threadReadService.getThreadsByForum(forum, keyset);
    }

    @GetMapping(value = "threads/{id}")
    public ThreadDTO getThreadDetails(@PathVariable("id") Integer id) {
        return threadReadService.getThread(id);
    }

    @GetMapping(value = "users/{user}/threads")
    public List<ThreadDTO> getThreads(@PathVariable("user") Integer user, TimestampKeyset epoch) {
        return threadReadService.getByUserID(user, epoch);
    }

    @PutMapping("threads/{threadID}")
    @Secured("ROLE_USER")
    public ThreadDTO modifyThread(@PathVariable Integer threadID, @RequestParam String content) {
        return threadWriteService.modifyThread(threadID, content);
    }

}
