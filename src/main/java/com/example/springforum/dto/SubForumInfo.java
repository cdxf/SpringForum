package com.example.springforum.dto;

import com.example.springforum.entity.Thread;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class SubForumInfo {
    Integer id;
    String name;
    String description;
    Thread lastThread;
    Long threadCount;
}
