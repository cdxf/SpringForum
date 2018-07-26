package com.springforum.thread.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * New Thread Payload DTO for create new thread
 */
public interface NewThreadPayload {
    @NotNull
    String getTitle();

    @NotNull
    Integer getForumId();

    @NotNull @Length(min = 20)
    String getContent();
}
