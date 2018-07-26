package com.springforum.forum;

import com.springforum.forum.dto.ForumBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends PagingAndSortingRepository<Forum, Integer> {
    @Query("select " +
            "new com.springforum.forum.dto.ForumBase(" +
            "forum.id," +
            "forum.name," +
            "forum.description," +
            "forum.parent.id," +
            "forum.latestThread.id," +
            "forum.latestThread.title," +
            "forum.latestThread.content," +
            "author.id," +
            "author.username," +
            "author.avatar," +
            "forum.threads," +
            "forum.posts) " +
            "from Forum forum " +
            "inner join forum.latestThread.author as author")
    Iterable<ForumBase> getAll();

    @Query("select " +
            "new com.springforum.forum.dto.ForumBase(" +
            "forum.id," +
            "forum.name," +
            "forum.description," +
            "forum.parent.id," +
            "forum.latestThread.id," +
            "forum.latestThread.title," +
            "forum.latestThread.content," +
            "author.id," +
            "author.username," +
            "author.avatar," +
            "forum.threads," +
            "forum.posts" +
            ") " +
            "from Forum forum " +
            "inner join forum.latestThread.author as author " +
            "where forum.id=?1")
    ForumBase getById(Integer id);

    @Query("select forum " +
            "from Forum forum " +
            "where forum.parent.id is NULL ")
    Iterable<Forum> findAllRoot();

}
