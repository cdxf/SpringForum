package com.springforum.thread.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * New Thread Payload DTO for create new thread
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewThreadCommand {
    @NotNull public String title;

    @NotNull public Integer forumId;

    @NotNull @Length(min = 20) public String content;
}
