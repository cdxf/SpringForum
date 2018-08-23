package com.springforum.thread;

import com.springforum.forum.dto.ForumDTO;
import com.springforum.generic.TimestampKeyset;
import com.springforum.tables.Thread;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;
import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Repository("threadDao")
@Transactional
public class ThreadDAO extends ThreadMeta {
    @Autowired
    protected DSLContext create;

    public ThreadDAO(DSLContext create) {
        this.create = create;
    }

    public void increaseView(Integer id) {
        Thread thread = Thread.THREAD;
        create.update(thread).set(thread.VIEWS, thread.VIEWS.add(1))
                .where(thread.ID.eq(id))
                .execute();
    }

    public ThreadDTO mapper(Record record) {
        return ThreadDTO.builder()
                .id(record.get(thread.ID))
                .forum(ForumDTO.builder()
                        .id(record.get(forum.ID))
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
    public ThreadDTO findById(Integer id) {
        return create.select().from(thread)
                .innerJoin(author).on(author.ID.eq(thread.AUTHOR_ID))
                .innerJoin(forum).on(forum.ID.eq(thread.FORUM_ID))
                .where(thread.ID.eq(id))
                .limit(1)
                .fetchOne()
                .map(it -> ThreadDTO.builder()
                        .id(it.get(thread.ID))
                        .forum(ForumDTO.builder()
                                .id(it.get(forum.ID))
                                .build())
                        .author(UserSummary.builder()
                                .id(it.get(author.ID))
                                .username(it.get(author.USERNAME))
                                .avatar(it.get(author.AVATAR_ID))
                                .build())
                        .title(it.get(thread.TITLE))
                        .views(it.get(thread.VIEWS))
                        .comments(it.get(thread.COMMENTS))
                        .createdTime(it.get(thread.CREATED_TIME).toInstant())
                        .lastModified(it.get(thread.LAST_MODIFIED).toInstant())
                        .content(it.get(thread.CONTENT))
                        .build());
    }

    public List<ThreadDTO> getThreadsByForum(Integer id, TimestampKeyset keyset) {
        TimestampKeyset.CondtionAndOrder seek = keyset.seek(thread.LAST_MODIFIED);
        var query = create.select().from(thread)
                .innerJoin(author).on(author.ID.eq(thread.AUTHOR_ID))
                .leftOuterJoin(lastReply).on(thread.LAST_REPLY_ID.eq(lastReply.ID))
                .leftOuterJoin(lastReplyAuthor).on(lastReply.AUTHOR_ID.eq(lastReplyAuthor.ID))
                .leftOuterJoin(forum).on(forum.ID.eq(thread.FORUM_ID))
                .where(forum.ID.eq(id))
                .and(seek.condition)
                .orderBy(seek.order)
                .limit(20);
        return query.fetch().map(it -> ThreadDTO.builder()
                .id(it.get(thread.ID))
                .forum(ForumDTO.builder()
                        .id(it.get(forum.ID))
                        .build())
                .author(UserSummary.builder()
                        .username(it.get(author.USERNAME))
                        .avatar(it.get(author.AVATAR_ID))
                        .id(it.get(author.ID))
                        .build())
                .title(it.get(thread.TITLE))
                .comments(it.get(thread.COMMENTS))
                .views(it.get(thread.VIEWS))
                .createdTime(it.get(thread.CREATED_TIME).toInstant())
                .lastModified(it.get(thread.LAST_MODIFIED).toInstant())
                .summary(StringUtils.abbreviate(it.get(thread.CONTENT), 100))
                .build());
    }

    public List<ThreadDTO> findByUserId(Integer userID, TimestampKeyset keyset) {
        if (keyset == null) keyset = TimestampKeyset.getDefault();
        TimestampKeyset.CondtionAndOrder seek = keyset.seek(thread.LAST_MODIFIED);
        var query = create.select()
                .from(thread)
                .innerJoin(forum).on(forum.ID.eq(thread.FORUM_ID))
                .where(thread.AUTHOR_ID.eq(userID))
                .and(seek.condition)
                .orderBy(seek.order);
        return query.fetch()
                .map(it -> ThreadDTO.builder()
                        .id(it.get(thread.ID))
                        .forum(ForumDTO.builder()
                                .id(it.get(forum.ID))
                                .build())
                        .title(it.get(thread.TITLE))
                        .author(UserSummary.builder()
                                .username(it.get(author.USERNAME))
                                .avatar(it.get(author.AVATAR_ID))
                                .id(it.get(author.ID))
                                .build())
                        .createdTime(it.get(thread.CREATED_TIME).toInstant())
                        .lastModified(it.get(thread.LAST_MODIFIED).toInstant())
                        .content(it.get(thread.CONTENT))
                        .build());
    }

    public List<Instant> getRange(Integer forumId) {
        var query = create.select(thread.LAST_MODIFIED)
                .from(thread)
                .where(thread.FORUM_ID.eq(forumId))
                .orderBy(thread.LAST_MODIFIED.asc())
                .limit(1)
                .unionAll(
                        create.select(thread.LAST_MODIFIED).from(thread)
                                .where(thread.FORUM_ID.eq(forumId))
                                .orderBy(thread.LAST_MODIFIED.desc()).limit(1));
        return query.fetch().map(record -> record.get(thread.LAST_MODIFIED).toInstant());
    }
}
