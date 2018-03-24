package com.example.springforum.dto;

import com.example.springforum.entity.Forum;
import com.example.springforum.entity.Thread;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ForumSummary {
    @Getter private Integer id;
    @Getter private String name;
    @Getter private String description;
    Iterable<SubForumInfo> subforums;
    Iterable<Thread> threads;
    Iterable<Forum> path;
    public Boolean isEmpty(){return !hasSubForums() && ! hasThreads(); }
    public Boolean hasThreads(){ return threads.iterator().hasNext();}
    public Boolean hasSubForums(){ return subforums.iterator().hasNext();}
    public Boolean isRoot(){ return id == null;}
}
