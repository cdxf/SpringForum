package com.springforum;


import com.springforum.forum.ForumDao;
import com.springforum.forum.dto.ForumDTO;
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
public class ForumDaoTest {
    @Autowired ForumDao forumDao;

    @Test
    public void allForum() {
        List<ForumDTO> query = forumDao.getAllForum();
        query.forEach(it -> {
            assertThat(it).isNotNull();
            assertThat(it.getName()).isNotBlank();
            assertThat(it.getDescription()).isNotBlank();
            assertThat(it.getId()).isNotNegative();
            if (it.getLatestThread() != null) {
                assertThat(it.getLatestThread().getTitle()).isNotBlank();
                assertThat(it.getLatestThread().getAuthor()).isNotNull();
                assertThat(it.getLatestThread().getAuthor().getId()).isNotNegative();
                assertThat(it.getLatestThread().getAuthor().getUsername()).isNotBlank();
                assertThat(it.getLatestThread().getAuthor().getId()).isNotEqualTo(it.getId());
            }
            assertThat(it.getId()).isNotEqualTo(it.parent.getId());
        });
    }

    @Test
    public void forumByID() {
        List<ForumDTO> allforums = forumDao.getAllForum();
        var id = allforums.get(0).getId();
        var it = forumDao.getForumById(id);
        assertThat(it).isNotNull();
        assertThat(it.getName()).isNotBlank();
        assertThat(it.getDescription()).isNotBlank();
        assertThat(it.getId()).isNotNegative();
        if (it.getLatestThread() != null) {
            assertThat(it.getLatestThread().getAuthor()).isNotNull();
            assertThat(it.getLatestThread().getAuthor().getId()).isNotNegative();
            assertThat(it.getLatestThread().getAuthor().getUsername()).isNotBlank();
            assertThat(it.getLatestThread().getAuthor().getId()).isNotEqualTo(it.getId());
        }
        assertThat(it.getId()).isNotEqualTo(it.parent.getId());
    }
}
