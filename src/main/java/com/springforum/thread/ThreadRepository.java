package com.springforum.thread;

import com.springforum.thread.dto.ThreadBase;
import com.springforum.thread.dto.ThreadWithContent;
import com.springforum.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Integer> {
    String threadByIdQuery =
            "select new com.springforum.thread.dto.ThreadWithContent" +
                    "(" +
                    "t.id," +
                    "t.createdTime," +
                    "t.updatedTime, " +
                    "t.title," +
                    "t.lastModified," +
                    "t.author.id," +
                    "t.author.username, " +
                    "t.author.avatar," +
                    "t.comments," +
                    "t.views, t.content," +
                    "t.forum.id " +
                    ") " +
                    "from Thread t " +
                    "inner join t.author " +
                    "inner join t.forum " +
                    "where t.id = :id ";
    String threadsByIdQuery =
            "select thread " +
                    "from Thread thread " +
                    "inner join thread.author author " +
                    "where thread.id in :id";
    String threadsByForumQuery =
            "select thread " +
                    "from Thread thread join thread.author " +
                    "where thread.forum.id = :id " +
                    "order by thread.lastModified DESC";

    List<Thread> findThreadsByAuthor(User user);

    @Query(threadsByForumQuery)
    Page<Thread> getThreadsByForum(@Param("id") Integer forumID, Pageable page);

    @Query(threadByIdQuery)
    ThreadWithContent getThreadByById(@Param("id") Integer id);

    @Query(threadsByIdQuery)
    List<ThreadBase> getThreadsById(@Param("id") Set<Integer> ids);

    @Query("select new com.springforum.thread.dto.ThreadBase(" +
            "thread.id," +
            "thread.createdTime," +
            "thread.updatedTime," +
            "thread.lastModified," +
            "thread.title," +
            "thread.author.id, " +
            "thread.author.username," +
            "thread.author.avatar," +
            "thread.forum.id," +
            "thread.forum.name, " +
            "thread.comments," +
            "thread.views," +
            "lastReply.id,lastReply.updatedTime," +
            "lastReply.author.id,lastReply.author.username,lastReply.author.avatar) " +
            "from Thread thread " +
            "inner join thread.author " +
            "inner join thread.lastReply as lastReply " +
            "inner join thread.forum"
    )
    Page<ThreadBase> getLastestThread(Pageable page);

    @Modifying
    @Transactional
    @Query("update Thread thread set thread.views = thread.views + 1 where thread.id= ?1 ")
    void increaseViews(Integer id);
}
