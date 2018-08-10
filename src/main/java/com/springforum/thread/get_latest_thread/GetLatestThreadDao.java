package com.springforum.thread.get_latest_thread;

import com.springforum.comment.dto.SimpleComment;
import com.springforum.forum.dto.ForumDTO;
import com.springforum.generic.TimestampKeyset;
import com.springforum.thread.ThreadMeta;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("getLatestThreadDao")
@Slf4j
public class GetLatestThreadDao extends ThreadMeta {

    @Autowired
    protected DSLContext create;

    private ThreadDTO mapper(Record record) {
        return ThreadDTO.builder()
                .id(record.get(thread.ID))
                .forum(ForumDTO.builder()
                        .id(record.get(forum.ID))
                        .build())
                .lastReply(record.get(lastReply.ID) == null ? null : SimpleComment.builder()
                        .id(record.get(lastReply.ID))
                        .updatedTime(record.get(lastReply.UPDATED_TIME).toInstant())
                        .createdTime(record.get(lastReply.CREATED_TIME).toInstant())
                        .author(UserSummary.builder()
                                .id(record.get(lastReplyAuthor.ID))
                                .username(record.get(lastReplyAuthor.USERNAME))
                                .avatar(record.get(lastReplyAuthor.AVATAR_ID))
                                .createdTime(record.get(lastReplyAuthor.CREATED_TIME).toInstant())
                                .build())
                        .build())
                .author(UserSummary.builder()
                        .id(record.get(author.ID))
                        .username(record.get(author.USERNAME))
                        .avatar(record.get(author.AVATAR_ID))
                        .build())
                .title(record.get(thread.TITLE))
                .views(record.get(thread.VIEWS))
                .comments(record.get(thread.COMMENTS))
                .createdTime(record.get(thread.CREATED_TIME).toInstant())
                .lastModified(record.get(thread.LAST_MODIFIED).toInstant())
                .content(record.get(thread.CONTENT))
                .build();
    }

    public List<ThreadDTO> query(TimestampKeyset timestampKeyset) {
        if (timestampKeyset == null) timestampKeyset = TimestampKeyset.getDefault();
        TimestampKeyset.CondtionAndOrder seek = timestampKeyset.seek(thread.LAST_MODIFIED);
        var query = create
                .select()
                .from(thread)
                .innerJoin(author).on(author.ID.eq(thread.AUTHOR_ID))
                .innerJoin(forum).on(forum.ID.eq(thread.FORUM_ID))
                .leftOuterJoin(lastReply).on(lastReply.ID.eq(thread.LAST_REPLY_ID))
                .leftOuterJoin(lastReplyAuthor).on(lastReplyAuthor.ID.eq(lastReply.AUTHOR_ID))
                .where(seek.condition)
                .orderBy(seek.order)
                .limit(20);
        log.info("SQL " + query.getSQL());
        return query.fetch().map(this::mapper);
    }
}
