package com.springforum.forum;

import com.springforum.Tables;
import com.springforum.forum.dto.ForumDTO;
import com.springforum.tables.Forum;
import com.springforum.tables.Thread;
import com.springforum.tables.Users;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "ForumDao")
@Slf4j
@Getter
@Setter
public class ForumDao {

    private Forum forum = Tables.FORUM.as("forum");
    private Forum parent = Tables.FORUM.as("parent");
    private Thread latestThread = Tables.THREAD.as("latest_thread");
    private Users latestThreadAuthor = Tables.USERS.as("latest_thread_author");

    @Autowired private DSLContext create;

    private ForumDTO recordMapper(Record record) {
        var forumBaseBuilder = ForumDTO.builder()
                .id(record.get(forum.ID))
                .name(record.get(forum.NAME))
                .description(record.get(forum.DESCRIPTION))
                .posts(record.get(forum.POSTS))
                .threads(record.get(forum.THREADS));
        if (record.get(latestThread.ID) != null) {
            forumBaseBuilder = forumBaseBuilder
                    .latestThread(ThreadDTO.builder()
                            .id(record.get(latestThread.ID))
                            .title(record.get(latestThread.TITLE))
                            .createdTime(record.get(latestThread.CREATED_TIME).toInstant())
                            .updatedTime(record.get(latestThread.UPDATED_TIME).toInstant())
                            .lastModified(record.get(latestThread.LAST_MODIFIED).toInstant())
                            .author(UserSummary.builder()
                                    .username(record.get(latestThreadAuthor.USERNAME))
                                    .avatar(record.get(latestThreadAuthor.AVATAR_ID))
                                    .id(record.get(latestThreadAuthor.ID))
                                    .build()).build());
        }
        return forumBaseBuilder
                .parent(ForumDTO.builder()
                        .id(record.get(parent.ID))
                        .build())
                .build();
    }

    public ForumDTO getForumById(Integer id) {
        var query = create.select().from(forum)
                .leftOuterJoin(parent).on(parent.ID.eq(forum.PARENT_ID))
                .leftOuterJoin(latestThread).on(forum.LATEST_THREAD.eq(latestThread.ID))
                .leftOuterJoin(latestThreadAuthor).on(latestThreadAuthor.ID.eq(latestThread.AUTHOR_ID))
                .where(forum.ID.eq(id)).limit(1);
        log.debug(query.getSQL());
        return query.fetchOne().map(this::recordMapper);
    }

    public List<ForumDTO> getAllForum() {
        var query = create
                .select()
                .from(forum)
                .leftOuterJoin(parent).on(parent.ID.eq(forum.PARENT_ID))
                .leftOuterJoin(latestThread).on(forum.LATEST_THREAD.eq(latestThread.ID))
                .leftOuterJoin(latestThreadAuthor).on(latestThreadAuthor.ID.eq(latestThread.AUTHOR_ID));
        log.debug(query.getSQL());
        return query.fetch().map(this::recordMapper);
    }
}
