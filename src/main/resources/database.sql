
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE "avatar" (
    id integer NOT NULL,
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    data bytea,
    random double precision NOT NULL
);



CREATE TABLE "comment" (
    id integer NOT NULL,
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    content character varying(10485760),
    author_id integer NOT NULL,
    thread_id integer NOT NULL
);




CREATE TABLE "forum"(
    id integer NOT NULL,
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    description character varying(255),
    name character varying(255),
    posts integer,
    threads integer,
    latest_thread integer,
    parent_id integer
);




CREATE TABLE "page_hits" (
    id integer NOT NULL,
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    ip character varying(255)
);




CREATE TABLE "thread" (
    id integer NOT NULL,
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    comments integer,
    content character varying(10485760),
    last_modified timestamp without time zone,
    title character varying(255),
    views integer,
    author_id integer NOT NULL,
    forum_id integer NOT NULL,
    last_reply_id integer
);




CREATE TABLE "user_activity" (
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    last_thread_creation timestamp without time zone,
    user_id integer NOT NULL
);




CREATE TABLE "user_role" (
    user_id integer NOT NULL,
    role character varying(255)
);


CREATE TABLE "users" (
    id integer NOT NULL,
    created_time timestamp without time zone,
    updated_time timestamp without time zone,
    comments integer,
    email character varying(255),
    password character varying(255) NOT NULL,
    threads integer,
    username character varying(255) NOT NULL,
    avatar_id integer
);



CREATE SEQUENCE "sequence";


ALTER TABLE "avatar"
    ADD CONSTRAINT avatar_pkey PRIMARY KEY (id);


ALTER TABLE "comment"
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);



ALTER TABLE "forum"
    ADD CONSTRAINT forum_pkey PRIMARY KEY (id);



ALTER TABLE "page_hits"
    ADD CONSTRAINT page_hits_pkey PRIMARY KEY (id);


ALTER TABLE "thread"
    ADD CONSTRAINT thread_pkey PRIMARY KEY (id);



ALTER TABLE "users"
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


ALTER TABLE "users"
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


ALTER TABLE "users"
    ADD CONSTRAINT uk_sx468g52bpetvlad2j9y0lptc UNIQUE (username);



ALTER TABLE "user_activity"
    ADD CONSTRAINT user_activity_pkey PRIMARY KEY (user_id);


ALTER TABLE "users"
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);



CREATE INDEX comment_author_id ON "comment" (author_id);


CREATE INDEX comment_thread_id ON "comment" (thread_id);



CREATE INDEX createdtimeindex ON "page_hits" (created_time);


CREATE INDEX lastmodified ON "thread" (last_modified);


CREATE INDEX latestthreadindex ON "forum" (latest_thread);


CREATE INDEX random ON "avatar" (random);



CREATE INDEX thread_author_id ON "thread" (author_id);


CREATE INDEX thread_forum_id ON "thread" (forum_id);



ALTER TABLE "users"
    ADD CONSTRAINT fk5yr86wseqtg71i7w0un54mr9 FOREIGN KEY (avatar_id) REFERENCES "avatar"(id);



ALTER TABLE "thread"
    ADD CONSTRAINT fk91yd4uq5lexcwhhi1orfs7nn FOREIGN KEY (forum_id) REFERENCES "forum"(id);


ALTER TABLE "thread"
    ADD CONSTRAINT fkatqlyrrir5n0970ngtv2vy0qk FOREIGN KEY (last_reply_id) REFERENCES "comment"(id);



ALTER TABLE "forum"
    ADD CONSTRAINT fkbfh0b305scq6mnvnhnfsk20fq FOREIGN KEY (parent_id) REFERENCES "forum"(id);



ALTER TABLE "comment"
    ADD CONSTRAINT fkehf7mvstlwwl8fy9ahfo515rm FOREIGN KEY (thread_id) REFERENCES "thread"(id);


ALTER TABLE "comment"
    ADD CONSTRAINT fkir20vhrx08eh4itgpbfxip0s1 FOREIGN KEY (author_id) REFERENCES "users"(id);



ALTER TABLE "forum"
    ADD CONSTRAINT fkivi7mwasgbs9qb19r1oe337wx FOREIGN KEY (latest_thread) REFERENCES "thread"(id);



ALTER TABLE "user_role"
    ADD CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id) REFERENCES "users"(id);



ALTER TABLE "user_activity"
    ADD CONSTRAINT fks41is1raa3f0y5q5g0pw2rfd4 FOREIGN KEY (user_id) REFERENCES "users"(id);


ALTER TABLE "thread"
    ADD CONSTRAINT fksoku9fxjl3uunwb3wdwkegv43 FOREIGN KEY (author_id) REFERENCES "users"(id);
