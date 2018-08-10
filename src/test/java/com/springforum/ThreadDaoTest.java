package com.springforum;


import com.springforum.thread.ThreadDAO;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.thread.get_latest_thread.GetLatestThreadDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ThreadDaoTest {
    @Autowired ThreadDAO threadDAO;
    @Autowired
    GetLatestThreadDao latestThreadDao;

    @Test
    public void threadByID() {
        List<ThreadDTO> threads = latestThreadDao.query(null);
        threads.stream().limit(10).forEach(it -> {
            ThreadDTO thread = threadDAO.findById(it.getId());
            assertThat(thread.getId()).isNotNegative();
            assertThat(thread.getComments()).isNotNegative();
            assertThat(thread.getViews()).isNotNegative();
            assertThat(thread.getAuthor()).isNotNull();
            assertThat(thread.getAuthor().getId()).isNotNegative();
            assertThat(thread.getAuthor().getUsername()).isNotBlank();
            assertThat(thread.getForum()).isNotNull();
            assertThat(thread.getForum().getId()).isNotNegative();

        });
    }
}
