package com.springforum.search;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.thread.dto.ThreadDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    SearchDAO.ThreadSearch threadSearch;
    @Autowired
    SearchDAO.CommentSearch commentSearch;

    @GetMapping
    public SearchResult search(@RequestParam("query") String query) {
        var threads = threadSearch.search(query);
        var comments = commentSearch.search(query);
        return new SearchResult(threads, comments);
    }

    @AllArgsConstructor
    class SearchResult {
        public List<ThreadDTO> threads;
        public List<CommentWithThread> comments;
    }
}
