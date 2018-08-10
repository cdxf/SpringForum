package com.springforum.thread.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * New Thread Payload DTO for create new thread
 */
@Data
public class NewThreadCommand {
    @NotNull final public String title;

    @NotNull final public Integer forumId;

    @NotNull @Length(min = 20) final public String content;
}
