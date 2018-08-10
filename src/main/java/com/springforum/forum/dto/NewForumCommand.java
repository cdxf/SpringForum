package com.springforum.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewForumCommand {
    final String name;

    final String description;

    final Integer parentForumId;

}

