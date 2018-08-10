package com.springforum.thread;

import com.springforum.Tables;
import com.springforum.tables.Comment;
import com.springforum.tables.Forum;
import com.springforum.tables.Thread;
import com.springforum.tables.Users;

public class ThreadMeta {
    protected com.springforum.tables.Thread thread = Thread.THREAD;
    protected Users author = Users.USERS.as("author");
    protected Forum forum = Forum.FORUM.as("forum");
    protected Comment lastReply = Tables.COMMENT.as("last_reply");
    protected Users lastReplyAuthor = Users.USERS.as("lastReplyAuthor");

}
