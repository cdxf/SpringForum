package com.springforum.forum;

import com.springforum.forum.dto.ForumDTO;
import com.springforum.generic.Benchmark;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@Setter
@RequestMapping("/api")
public class ForumAPI {
    @Autowired
    private ForumService forumService;

    @GetMapping("forums")
    public Iterable<ForumDTO> all() throws Exception {
        return Benchmark.call(forumService::getForums);
    }


    @GetMapping("forums/{id}")
    public ForumDTO getForum(@PathVariable("id") Integer id) throws Exception {
        return Benchmark.call(() -> forumService.getForumDTO(id));
    }
}
