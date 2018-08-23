package com.springforum.comment.read_comments_by_thread_id;

import com.springforum.comment.dto.SimpleComment;
import com.springforum.generic.TimestampKeyset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class CommentsByThreadController {
    @Autowired private CommentsByThreadDAO dao;

    public CommentsByThreadController(CommentsByThreadDAO dao) {
        this.dao = dao;
    }

    @GetMapping("api/threads/{thread}/comments")
    public List<SimpleComment> getCommentsByThreadId(@PathVariable(value = "thread") Integer thread, TimestampKeyset keyset) throws Exception {
        return dao.get(thread, keyset);
    }

    @GetMapping(value = "api/threads/{thread}/comments/range")
    public List<Instant> get(@PathVariable(value = "thread") Integer thread) {
        return dao.getRange(thread);
    }
}
