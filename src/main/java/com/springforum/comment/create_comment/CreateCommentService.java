package com.springforum.comment.create_comment;

import com.springforum.comment.Comment;
import com.springforum.comment.CommentMapper;
import com.springforum.comment.dto.CommentWithThread;
import com.springforum.thread.Thread;
import com.springforum.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Validated
public class CreateCommentService {
    @Autowired private EntityManager entityManager;

    public Optional<CommentWithThread> createComment(@NotNull Integer threadID, @NotBlank String content, @NotNull Integer userId) {
        var user = entityManager.find(User.class, userId);
        var thread = entityManager.find(Thread.class, threadID);
        var comment = new Comment(content, thread, user);
        thread.setComments(thread.getComments() + 1);
        thread.setLastReply(comment);
        var forum = thread.getForum();
        while (forum != null) {
            forum.setPosts(forum.getPosts() + 1);
            forum = forum.getParent();
        }
        user.setComments(user.getComments() + 1);
        entityManager.persist(comment);
        CommentWithThread result = CommentMapper.commentToCommentWithThread(comment);
        return Optional.ofNullable(result);
    }

    public void createMultipleComments(Integer threadID, List<String> contents, List<Integer> userIds) {
        if (contents.size() != userIds.size())
            throw new IllegalArgumentException("contents size must be the same as userIds size");
        var thread = entityManager.find(Thread.class, threadID);
        Comment lastReplies = null;
        for (int i = 0; i < contents.size(); i++) {
            var user = entityManager.find(User.class, userIds.get(i));
            var comment = new Comment(contents.get(i), thread, user);
            user.setComments(user.getComments() + 1);
            entityManager.persist(comment);
            lastReplies = comment;
        }
        var forum = thread.getForum();
        while (forum != null) {
            forum.setPosts(forum.getPosts() + contents.size());
            forum = forum.getParent();
        }
        thread.setComments(thread.getComments() + contents.size());
        thread.setLastReply(lastReplies);

    }
}
