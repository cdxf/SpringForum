package com.springforum.thread;

import com.springforum.thread.dto.ThreadBase;
import com.springforum.thread.dto.ThreadWithContent;

public abstract class ThreadMaper {
    public static ThreadBase threadToThreadBase(Thread value) {
        ThreadBase build = ThreadBase.builder()
                .id(value.getId())
                .authorId(value.getAuthor().getId())
                .authorName(value.getAuthor().getUsername())
                .authorAvatar(value.getAuthor().getAvatar())
                .comments(value.getComments())
                .forumId(value.getForum().getId())
                .forumName(value.getForum().getName())
                .title(value.getTitle())
                .lastModified(value.getLastModified())
                .createdTime(value.getCreatedTime())
                .updatedTime(value.getUpdatedTime())
                .views(value.getViews()).build();
        return build;
    }

    public static ThreadWithContent threadsThreadWithContent(Thread value) {

        ThreadWithContent build = ThreadWithContent.builder()
                .id(value.getId())
                .authorId(value.getAuthor().getId())
                .authorName(value.getAuthor().getUsername())
                .authorAvatar(value.getAuthor().getAvatar())
                .comments(value.getComments())
                .content(value.getContent())
                .createdTime(value.getCreatedTime())
                .updatedTime(value.getUpdatedTime())
                .lastModified(value.getLastModified())
                .title(value.getTitle())
                .views(value.getViews())
                .build();
        return build;
    }
}