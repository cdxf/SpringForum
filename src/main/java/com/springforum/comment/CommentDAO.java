package com.springforum.comment;

import com.springforum.Tables;
import com.springforum.comment.dto.CommentWithThread;
import com.springforum.generic.TimestampKeyset;
import com.springforum.tables.Comment;
import com.springforum.tables.Thread;
import com.springforum.tables.Users;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
@Getter
@Setter
@Slf4j
public class CommentDAO {
    @Autowired
    protected DSLContext dslContext;
    protected Comment comment = Tables.COMMENT;
    protected Users author = Tables.USERS.as("comment_author");
    protected Thread threads = Tables.THREAD.as("thread_author");

    public CommentWithThread getCommentById(Integer id) {
        var fetch = dslContext.select().from(comment)
                .innerJoin(author).on(comment.AUTHOR_ID.eq(author.ID))
                .innerJoin(threads).on(threads.ID.eq(comment.THREAD_ID))
                .where(comment.ID.eq(id))
                .limit(1);
        final CommentWithThread map = fetch
                .fetchOne()
                .map(it -> CommentWithThread.builder()
                        .id(it.get(comment.ID))
                        .content(it.get(comment.CONTENT))
                        .thread(ThreadDTO.builder()
                                .id(it.get(threads.ID))
                                .createdTime(it.get(threads.CREATED_TIME).toInstant())
                                .title(it.get(threads.TITLE))
                                .build())
                        .updatedTime(it.get(comment.UPDATED_TIME).toInstant())
                        .createdTime(it.get(comment.CREATED_TIME).toInstant())
                        .author(UserSummary.builder()
                                .id(it.get(author.ID))
                                .username(it.get(author.USERNAME))
                                .avatar(it.get(author.AVATAR_ID))
                                .build())
                        .build());
        return map;
    }


    public List<CommentWithThread> getLatestComments(TimestampKeyset timestampKeyset) {
        if (timestampKeyset == null) timestampKeyset = TimestampKeyset.getDefault();
        TimestampKeyset.CondtionAndOrder seek = timestampKeyset.seek(comment.UPDATED_TIME);
        var query = dslContext.select().from(comment)
                .innerJoin(author).on(comment.AUTHOR_ID.eq(author.ID))
                .innerJoin(threads).on(threads.ID.eq(comment.THREAD_ID))
                .where(seek.condition)
                .orderBy(seek.order)
                .limit(20);
        log.info(query.getSQL());
        var result = query.fetch().map(it -> CommentWithThread.builder()
                .id(it.get(comment.ID))
                .content(it.get(comment.CONTENT))
                .updatedTime(it.get(comment.UPDATED_TIME).toInstant())
                .createdTime(it.get(comment.CREATED_TIME).toInstant())
                .thread(ThreadDTO.builder()
                        .id(it.get(threads.ID))
                        .createdTime(it.get(threads.CREATED_TIME).toInstant())
                        .title(it.get(threads.TITLE))
                        .build())
                .author(UserSummary.builder()
                        .id(it.get(author.ID))
                        .username(it.get(author.USERNAME))
                        .avatar(it.get(author.AVATAR_ID))
                        .build())
                .build());
        result.sort(Comparator.comparing(CommentWithThread::getUpdatedTime).reversed());
        return result;
    }

}
