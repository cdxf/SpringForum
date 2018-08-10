package com.springforum.comment.dto;

import com.springforum.user.dto.UserSummary;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleComment {
    public Integer id;
    public Instant createdTime;
    public Instant updatedTime;
    public String content;
    public UserSummary author;
}
