package com.springforum.forum.dto;

public interface ForumCreatePayload {
    String getName();

    String getDescription();

    Integer getParentForumId();
}

