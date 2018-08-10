package com.springforum.comment.read_comments_by_thread_id;

import com.springforum.comment.CommentDAO;
import com.springforum.comment.dto.SimpleComment;
import com.springforum.generic.TimestampKeyset;
import com.springforum.user.dto.UserSummary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentsByThreadDAO extends CommentDAO {

    public List<SimpleComment> get(Integer threadID, TimestampKeyset timestampKeyset) {
        if (timestampKeyset == null) timestampKeyset = TimestampKeyset.getDefault();
        TimestampKeyset.CondtionAndOrder seek = timestampKeyset.seek(comment.UPDATED_TIME);
        var fetch = dslContext.select().from(comment)
                .innerJoin(author).on(comment.AUTHOR_ID.eq(author.ID))
                .where(comment.THREAD_ID.eq(threadID)).and(seek.condition)
                .orderBy(seek.order)
                .limit(20)
                .fetch();
        return fetch.map(it -> SimpleComment.builder()
                .id(it.get(comment.ID))
                .content(it.get(comment.CONTENT))
                .updatedTime(it.get(comment.UPDATED_TIME).toInstant())
                .createdTime(it.get(comment.CREATED_TIME).toInstant())
                .author(UserSummary.builder()
                        .id(it.get(author.ID))
                        .username(it.get(author.USERNAME))
                        .avatar(it.get(author.AVATAR_ID))
                        .build())
                .build());
    }
}
