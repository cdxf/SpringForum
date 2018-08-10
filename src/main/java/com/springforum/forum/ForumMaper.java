package com.springforum.forum;

import com.springforum.forum.dto.ForumDTO;
import com.springforum.thread.ThreadMaper;


public abstract class ForumMaper {
    public static ForumDTO forumToForumBase(Forum value) {
        Integer parentId = value.getParent() != null ? value.getParent().getId() : null;
        return ForumDTO.builder()
                .description(value.getDescription())
                .id(value.getId())
                .posts(value.getPosts())
                .threads(value.getThreads())
                .latestThread(ThreadMaper.threadDTOMapper(value.getLatestThread()))
                .name(value.getName())
                .parent(ForumDTO.builder()
                        .id(parentId)
                        .build())
                .build();
    }
}