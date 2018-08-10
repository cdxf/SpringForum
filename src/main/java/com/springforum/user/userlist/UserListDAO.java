package com.springforum.user.userlist;

import com.springforum.generic.TimestampKeyset;
import com.springforum.tables.Users;
import com.springforum.user.dto.UserSummary;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserListDAO {
    @Autowired
    private DSLContext create;

    public List<UserSummary> query(TimestampKeyset timestampKeyset) {
        Users users = Users.USERS.as("users");
        TimestampKeyset.CondtionAndOrder seek = timestampKeyset.seek(users.UPDATED_TIME);
        var query = create
                .select()
                .from(users)
                .where(seek.condition)
                .orderBy(seek.order)
                .limit(20);
        log.info(query.getSQL());
        Result<Record> fetch = query.fetch();
        return fetch.map(it -> UserSummary.builder()
                .id(it.get(users.ID))
                .avatar(it.get(users.AVATAR_ID))
                .username(it.get(users.USERNAME))
                .createdTime(it.get(users.CREATED_TIME).toInstant())
                .threads(it.get(users.THREADS))
                .comments(it.get(users.COMMENTS))
                .build());
    }
}
