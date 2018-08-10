package com.springforum.forum.create_new_forum;

import com.springforum.forum.Forum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
@Transactional
public class CreateForumService {
    @Autowired private EntityManager entityManager;

    @Transactional
    public Optional<Forum> newForum(@NotNull String name, String description, Integer parentID) {
        var parent = parentID == null ? null : entityManager.getReference(Forum.class, parentID);
        var forum = new Forum(name, description, parent);
        return Optional.ofNullable(entityManager.merge(forum));
    }
}
