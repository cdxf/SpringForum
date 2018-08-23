package com.springforum.search;


import com.springforum.comment.CommentMeta;
import com.springforum.comment.dto.CommentWithThread;
import com.springforum.forum.dto.ForumDTO;
import com.springforum.thread.ThreadMeta;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.dto.UserSummary;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SearchDAO {

    @Component
    class ThreadSearch extends ThreadMeta {
        @Autowired
        DSLContext context;

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

        public List<ThreadDTO> search(String query) {
            var tsvector = " to_tsvector('english', title || ' ' || content) ";
            query = query.trim();
            var order = DSL
                    .field("ts_rank(" + tsvector + ", plainto_tsquery(?))", query).desc();
            var sql = context.select().from(thread)
                    .innerJoin(author).on(author.ID.eq(thread.AUTHOR_ID))
                    .innerJoin(forum).on(forum.ID.eq(thread.FORUM_ID))
                    .where(tsvector + " @@ plainto_tsquery(?)", query)
                    .orderBy(order)
                    .limit(10);
            System.out.println(sql.getSQL());
            return sql
                    .fetch()
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
    }

    @Component
    class CommentSearch extends CommentMeta {
        @Autowired
        DSLContext context;

        public List<CommentWithThread> search(String query) {
            query = query.trim();
            String where = "to_tsvector('english', " + comment.CONTENT.getQualifiedName().unquotedName() + ") @@ plainto_tsquery(?)";
            return context.select().from(comment)
                    .innerJoin(author).on(author.ID.eq(comment.AUTHOR_ID))
                    .innerJoin(thread).on(thread.ID.eq(comment.THREAD_ID))
                    .where(where, query)
                    .limit(10)
                    .fetch()
                    .map(it -> CommentWithThread.builder()
                            .id(it.get(comment.ID))
                            .thread(ThreadDTO.builder()
                                    .id(it.get(thread.ID))
                                    .title(it.get(thread.TITLE))
                                    .build())
                            .author(UserSummary.builder()
                                    .id(it.get(author.ID))
                                    .username(it.get(author.USERNAME))
                                    .avatar(it.get(author.AVATAR_ID))
                                    .build())
                            .createdTime(it.get(comment.CREATED_TIME).toInstant())
                            .updatedTime(it.get(comment.UPDATED_TIME).toInstant())
                            .content(it.get(comment.CONTENT))
                            .build());
        }
    }
}
