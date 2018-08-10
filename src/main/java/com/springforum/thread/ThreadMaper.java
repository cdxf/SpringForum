package com.springforum.thread;

import com.springforum.forum.dto.ForumDTO;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;

public abstract class ThreadMaper {
    public static ThreadDTO threadDTOMapper(Thread value) {
        return ThreadDTO.builder()
                .id(value.getId())
                .author(UserSummary.builder()
                        .username(value.getAuthor().getUsername())
                        .id(value.getAuthor().getId())
                        .avatar(value.getAuthor().getAvatar_id())
                        .build())
                .comments(value.getComments())
                .forum(ForumDTO.builder()
                        .id(value.getForum().getId())
                        .name(value.getForum().getName())
                        .description(value.getForum().getDescription())
                        .build())
                .title(value.getTitle())
                .content(value.getContent())
                .lastModified(value.getLastModified())
                .createdTime(value.getCreatedTime())
                .updatedTime(value.getUpdatedTime())
                .views(value.getViews()).build();
    }
}