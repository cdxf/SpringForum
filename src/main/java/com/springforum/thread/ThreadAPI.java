package com.springforum.thread;


import com.springforum.generic.BindingErrorDTO;
import com.springforum.generic.PathItemDTO;
import com.springforum.thread.dto.NewThreadPayload;
import com.springforum.thread.dto.ThreadBase;
import com.springforum.thread.dto.ThreadWithContent;
import com.springforum.thread.event.onThreadView;
import com.springforum.user.User;
import com.springforum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/threads")
public class ThreadAPI {
    @Autowired
    private ThreadService threadService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping(value = "", params = {"forumID"})
    public Iterable<ThreadWithContent> getByForum(@RequestParam("forumID") Integer forumID, Pageable pageable) {
        var threadsByForum = threadService.getThreadsByForum(forumID, pageable);
        return threadsByForum;
    }

    @GetMapping
    public Page<ThreadBase> getLastest(Pageable page) {
        return threadService.getLastest(page);
    }

    @GetMapping("/path/{id}")
    public Iterable<PathItemDTO> getPath(@PathVariable("id") Integer id) {
        return threadService.getPath(id);
    }

    @GetMapping("/{id}")
    public Optional<ThreadWithContent> getThreadDetails(@PathVariable("id") Integer id) {
        applicationEventPublisher.publishEvent(new onThreadView(this, id));
        return threadService.getThread(id);
    }

    @GetMapping(params = "id")
    public Iterable<ThreadBase> getThreads(@RequestParam("id") Set<Integer> ids) {
        if (ids.size() > 50) return List.of();
        return threadService.getThread(ids);
    }

    @PutMapping("/{threadID}")
    @Secured("ROLE_USER")
    public ThreadWithContent modifyThread(@PathVariable Integer threadID, @RequestParam String content) {
        return threadService.modifyThread(threadID, content, userService.getCurrentUser().getId());
    }

    @PostMapping("/")
    @Secured("ROLE_USER")
    public Object newThread(@Valid NewThreadPayload thread, BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().stream().map(it -> {
                if (it instanceof FieldError) {
                    FieldError error = (FieldError) it;
                    return new BindingErrorDTO(error.getField(), error.getDefaultMessage());
                } else return new BindingErrorDTO(null, it.getDefaultMessage());
            });
        }
        User currentUser = userService.getCurrentUser();
        ThreadWithContent threadCreation = threadService.newThread(thread.getTitle(), thread.getContent(), thread.getForumId(), currentUser.getId());
        return threadCreation;
    }
}
