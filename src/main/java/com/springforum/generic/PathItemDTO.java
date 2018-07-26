package com.springforum.generic;

import com.springforum.forum.Forum;
import com.springforum.thread.Thread;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathItemDTO {
    public Integer id;
    public Type type;
    public String name;
    ;

    public static PathItemDTO buildThreadPathItem(Thread thread) {
        return PathItemDTO.builder().id(thread.getId()).name(thread.getTitle())
                .type(PathItemDTO.Type.THREAD).build();
    }

    public static PathItemDTO buildForumPathItem(Forum forum) {
        return PathItemDTO.builder().id(forum.getId()).name(forum.getName())
                .type(Type.FORUM).build();
    }

    public enum Type {FORUM, THREAD}
}
