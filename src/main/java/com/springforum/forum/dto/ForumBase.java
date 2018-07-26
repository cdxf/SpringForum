package com.springforum.forum.dto;

import com.springforum.thread.dto.ThreadWithContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumBase {
    Integer id;
    String name;
    String description;
    Integer threads = 0;
    Integer posts = 0;
    IdOnly parent;
    ThreadWithContent latestThread;

    public ForumBase(Integer id, String name, String description, Integer parent,
                     Integer latestThreadId, String latestThreadTitle, String latestThreadContent,
                     Integer lastestThreadAuthorId, String latestThreadAuthorName, String latestThreadAuthorAvatar,
                     Integer threads, Integer posts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parent = new IdOnly(parent);
        this.threads = threads;
        this.posts = posts;
        this.latestThread = ThreadWithContent.builder()
                .id(latestThreadId)
                .content(latestThreadContent)
                .title(latestThreadTitle)
                .authorName(latestThreadAuthorName)
                .authorId(lastestThreadAuthorId)
                .authorAvatar(latestThreadAuthorAvatar)
                .build();
    }
}
