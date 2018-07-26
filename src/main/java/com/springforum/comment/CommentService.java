package com.springforum.comment;

import com.springforum.comment.dto.CommentWithThread;
import com.springforum.comment.dto.SimpleComment;
import com.springforum.thread.Thread;
import com.springforum.user.User;
import com.springforum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final EntityManager entityManager;
    @Autowired
    private UserService userService;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            EntityManager entityManager) {
        this.commentRepository = commentRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Comment newComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Page<SimpleComment> getCommentsFromThread(Integer threadID, Pageable page) {
        var comments = commentRepository.getCommentsByThreadId(threadID, page);
        return comments;

//        Set<Integer> authorIDs = comments.stream().map(it -> it.getAuthor().getId()).collect(Collectors.toSet());
//        Map<Integer,UserSummary> authors = userService
//                .findAllById(authorIDs)
//                .stream()
//                .collect(Collectors.toMap(it->it.getId(), Function.identity()));
//        comments.forEach(comment->{
//            var author = authors.get(comment.getAuthor().getId());
//            comment.setAuthor(author);
//        });
        //  return comments;
    }

    public Optional<CommentWithThread> getComment(Integer commentID) {
        var comment = commentRepository.getCommentById(commentID);
        return Optional
                .ofNullable(comment)
                .map(it -> CommentMapper.commentToCommentWithThread(comment));
    }

    @Transactional
    public void postMultipleComment(Integer threadID, List<String> contents, List<Integer> userIds) {
        if (contents.size() != userIds.size())
            throw new IllegalArgumentException("contents size must be the same as userIds size");
        var thread = entityManager.find(Thread.class, threadID);
        Comment lastReplies = null;
        for (int i = 0; i < contents.size(); i++) {
            var user = entityManager.getReference(User.class, userIds.get(i));
            var comment = new Comment(contents.get(i), thread, user);
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

    @Transactional
    public Optional<CommentWithThread> postComment(Integer threadID, String content, Integer userId) {
        var user = entityManager.getReference(User.class, userId);
        var thread = entityManager.find(Thread.class, threadID);
        var comment = new Comment(content, thread, user);
        thread.setComments(thread.getComments() + 1);
        thread.setLastReply(comment);
        var forum = thread.getForum();
        while (forum != null) {
            forum.setPosts(forum.getPosts() + 1);
            forum = forum.getParent();
        }
        entityManager.persist(comment);
        CommentWithThread result = CommentMapper.commentToCommentWithThread(comment);
        return Optional.ofNullable(result);
    }
}
