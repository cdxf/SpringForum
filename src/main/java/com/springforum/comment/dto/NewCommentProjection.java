package com.springforum.comment.dto;

import javax.validation.constraints.NotNull;

/**
 * Comment Payload DTO used when creating new comment
 */
public interface NewCommentProjection {
    @NotNull
    Integer getThreadId();

    @NotNull
    String getContent();
}
