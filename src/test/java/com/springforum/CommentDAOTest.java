package com.springforum;

import com.springforum.comment.CommentDAO;
import com.springforum.comment.dto.CommentWithThread;
import com.springforum.comment.dto.SimpleComment;
import com.springforum.comment.read_comments_by_thread_id.CommentsByThreadDAO;
import com.springforum.generic.TimestampKeyset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentDAOTest {
    @Autowired private CommentDAO commentDAO;
    @Autowired private CommentsByThreadDAO commentsByThreadDAO;

    @Before
    public void setUp() {

    }

    @Test
    public void getCommentbyID() {
        var latestComment = commentDAO.getLatestComments(null).get(0);
        CommentWithThread commentById = commentDAO.getCommentById(latestComment.getId());
        assertThat(commentById).satisfies(comment -> {
            assertThat(comment.getId()).isNotNull();
            assertThat(comment.getContent()).isNotBlank();
            assertThat(comment.getThread()).satisfies(thread -> {
                assertThat(thread).isNotNull();
                assertThat(thread.getId()).isNotNegative();
            });
            assertThat(comment.getAuthor()).satisfies(author -> {
                assertThat(author).isNotNull();
                assertThat(author.getUsername()).isNotBlank();
                assertThat(author.getAvatar()).isNotNegative();
                assertThat(author.getId()).isNotNull();
            });
        });
        System.out.println(commentById.getThread().getId());
    }

    @Test
    public void getLatestCommentsOrderTest() {

        List<CommentWithThread> descOrder = commentDAO.getLatestComments(new TimestampKeyset() {
            @Override
            public Long getTimestamp() {
                return null;
            }

            @Override
            public Boolean getDesc() {
                return true;
            }
        });
        for (int i = 0; i < descOrder.size() - 1; i++) {
            assertThat(descOrder.get(i).getUpdatedTime()
                    .isBefore(descOrder.get(i + 1).getUpdatedTime())).isFalse();
        }

        List<CommentWithThread> ascOrder = commentDAO.getLatestComments(new TimestampKeyset() {
            @Override
            public Long getTimestamp() {
                return null;
            }

            @Override
            public Boolean getDesc() {
                return false;
            }
        });
        List<CommentWithThread> defaultDesc = commentDAO.getLatestComments(null);
        for (int i = 0; i < defaultDesc.size() - 1; i++) {
            assertThat(defaultDesc.get(i).getUpdatedTime()
                    .isBefore(defaultDesc.get(i + 1).getUpdatedTime())).isFalse();
        }
        for (int i = 0; i < ascOrder.size() - 1; i++) {
            assertThat(ascOrder.get(i).getUpdatedTime().isBefore(ascOrder.get(i + 1).getUpdatedTime())).isFalse();
        }
    }

    @Test
    public void getLatestCommentsTest() {
        List<CommentWithThread> latestComments = commentDAO.getLatestComments(null);
        assertThat(latestComments)
                .isNotNull()
                .satisfies(it -> assertThat(it.size() > 1).isTrue())
                .allSatisfy(comment -> {
                    assertThat(comment.getId()).isNotNegative();
                    assertThat(comment.getContent())
                            .isNotBlank()
                            .isNotNull()
                            .satisfies(it -> assertThat(it.length() > 20).isTrue());
                    assertThat(comment.getThread())
                            .isNotNull()
                            .satisfies(thread -> {
                                assertThat(thread.getId()).isNotNegative();
                                assertThat(thread.getTitle()).isNotBlank();
                            });
                });
    }

    @Test
    public void getCommentsByThreadID() {
        CommentWithThread commentWithThread = commentDAO.getLatestComments(null).get(0);

        List<SimpleComment> comments = commentsByThreadDAO.get(commentWithThread.getThread().getId(), null);
        assertThat(comments)
                .satisfies(it -> assertThat(it.size()).isGreaterThan(1))
                .allSatisfy(comment -> assertThat(comment.getAuthor())
                        .isNotNull()
                        .satisfies(author -> {
                            assertThat(author.getId()).isNotNegative();
                            assertThat(author.getUsername()).isNotBlank();
                            assertThat(author.getAvatar()).isNotNegative();
                        }));
    }
}
