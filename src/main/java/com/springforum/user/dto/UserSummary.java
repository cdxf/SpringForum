package com.springforum.user.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

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
    public List<String> roles;
    public Instant createdTime;
}
