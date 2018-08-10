package com.springforum.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Comment Payload DTO used when creating new comment
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentProjection {
    @NotNull
    Integer threadId;

    @NotNull
    @Size(min = 10)
    String content;
}
