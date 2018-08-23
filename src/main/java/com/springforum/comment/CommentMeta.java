package com.springforum.comment;

import com.springforum.Tables;
import com.springforum.tables.Thread;
import com.springforum.tables.Users;

public class CommentMeta {
    protected com.springforum.tables.Comment comment = Tables.COMMENT;
    protected Users author = Tables.USERS.as("comment_author");
    protected Thread thread = Tables.THREAD.as("thread_author");
}
