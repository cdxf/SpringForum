package com.springforum.forum;

import com.springforum.forum.dto.ForumBase;
import com.springforum.forum.dto.IdOnly;
import com.springforum.thread.ThreadMaper;


public abstract class ForumMaper {
    public static ForumBase forumToForumBase(Forum value) {
        Integer parentId = value.getParent() != null ? value.getParent().getId() : null;
        return ForumBase.builder()
                .description(value.getDescription())
                .id(value.getId())
                .latestThread(ThreadMaper.threadsThreadWithContent(value.getLatestThread()))
                .name(value.getName())
                .parent(new IdOnly(parentId))
                .build();
    }
}