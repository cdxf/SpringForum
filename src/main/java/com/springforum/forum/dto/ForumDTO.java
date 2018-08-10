package com.springforum.forum.dto;

import com.springforum.thread.dto.ThreadDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumDTO {
    public Integer id;
    public String name;
    public String description;
    public Integer threads;
    public Integer posts;
    public ForumDTO parent;
    public ThreadDTO latestThread;
}
