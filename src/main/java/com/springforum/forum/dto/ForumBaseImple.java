package com.springforum.forum.dto;

import com.springforum.thread.dto.ThreadBase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForumBaseImple {
    private Integer id;
    private String name;
    private String description;
    private ThreadBase latestThread;
}