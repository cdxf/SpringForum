package com.springforum.comment;

import com.springforum.comment.dto.CommentWithThread;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service(value = "commentService")
@Transactional(readOnly = true)
@Getter
@Setter
public class CommentService {
    @Autowired private EntityManager entityManager;
    @Autowired private CommentDAO commentDAO;

    public Optional<CommentWithThread> getComment(Integer commentID) {
        var comment = commentDAO.getCommentById(commentID);
        return Optional
                .ofNullable(comment);
    }
}
