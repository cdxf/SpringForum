package com.springforum.user.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSummary {
    public Integer id;
    public String username;
    public Integer avatar;
    public Integer threads;
    public Integer comments;
    public Instant createdTime;
}
