package com.springforum.comment;

import com.springforum.comment.dto.SimpleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {

    Comment getCommentById(@Param("commentId") Integer commentID);

    @Query("select new " +
            "com.springforum.comment.dto.SimpleComment(" +
            "c.id," +
            "c.createdTime," +
            "c.updatedTime," +
            "c.content," +
            "c.author.id," +
            "c.author.username," +
            "c.author.avatar" +
            ") from Comment c join c.author " +
            "where c.thread.id =:threadId")
    Page<SimpleComment> getCommentsByThreadId(@Param("threadId") Integer threadID, Pageable page);

    @Query("select " +
            "c.thread.id, " +
            "count(c.thread.id) " +
            "from Comment c group by c.thread.id " +
            "having c.thread.id in ?1")
    List<Object[]> countByThreadIds(Set<Integer> threadId);

    Integer countCommentsByThreadId(Integer threadId);
}
