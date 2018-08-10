package com.springforum.forum.create_new_forum;

import com.springforum.forum.Forum;
import com.springforum.forum.ForumDao;
import com.springforum.forum.dto.ForumDTO;
import com.springforum.forum.dto.NewForumCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class CreateForumController {
    @Autowired
    CreateForumService service;
    @Autowired
    ForumDao forumDao;

    @PostMapping("api/forums")
    public Optional<ForumDTO> create(@RequestBody NewForumCommand payload) {
        Optional<Forum> forum = service.newForum(payload.getName(), payload.getDescription(),
                payload.getParentForumId());
        log.debug("Created Forum: " + forum.get().getId());
        return forum.map(it -> forumDao.getForumById(it.getId()));
    }
}
