package com.springforum.forum;

import com.springforum.forum.dto.ForumBase;
import com.springforum.forum.dto.ForumCreatePayload;
import com.springforum.generic.Benchmark;
import com.springforum.generic.PathItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/forums")
public class ForumAPI {
    private final ForumService forumService;

    @Autowired
    public ForumAPI(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping
    public Iterable<ForumBase> getForums() {
        return Benchmark.call(forumService::getForums);
    }

    @GetMapping("/path/{id}")
    public Iterable<PathItemDTO> getPath(@PathVariable("id") Integer id) {
        return forumService.getPath(id);
    }

    @GetMapping("/{id}")
    public ForumBase getForum(@PathVariable("id") Integer id) {
        return Benchmark.call(() -> forumService.getForumDTO(id));
    }

    @PostMapping
    public Optional<ForumBase> create(@RequestBody ForumCreatePayload payload) {
        return forumService
                .add(payload.getName(), payload.getDescription(), payload.getParentForumId())
                .map(ForumMaper::forumToForumBase);
    }
}
