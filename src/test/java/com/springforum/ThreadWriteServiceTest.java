package com.springforum;

import com.springforum.generic.TimestampKeyset;
import com.springforum.thread.create_new_thread.CreateThreadService;
import com.springforum.thread.get_latest_thread.GetLatestThreadDao;
import com.springforum.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WithUserDetails(value = "snoob")
public class ThreadWriteServiceTest {
    @Autowired
    UserService userService;
    @Autowired private CreateThreadService createThreadService;
    @Autowired private GetLatestThreadDao latestThreadDao;

    @Before
    public void setUp() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void addThreadForumIDNull() {
        createThreadService.newThread("Hello World", "Hello World", null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void addThreadUserIdNull() {
        createThreadService.newThread("Hello World", "Hello World", 1, null);
    }


    @Test
    public void testLastestThread() {
        var lastest = latestThreadDao
                .query(TimestampKeyset.getDefault());
        assertThat(lastest).size()
                .isGreaterThan(0)
                .isLessThan(25);
        lastest.forEach(thread -> {
            assertThat(thread.getId()).isNotNegative();
            assertThat(thread.getComments()).isNotNegative();
            assertThat(thread.getViews()).isNotNegative();
            assertThat(thread.getAuthor()).isNotNull();
            assertThat(thread.getAuthor().getId()).isNotNull();
            if (thread.getLastReply() != null) {
                assertThat(thread.getLastReply().getAuthor()).isNotNull();
                assertThat(thread.getLastReply().getAuthor().getId()).isNotNegative();
                assertThat(thread.getLastReply().getAuthor().getUsername()).isNotBlank();
            }
            assertThat(thread.getForum()).isNotNull();
            assertThat(thread.getForum().getId()).isNotNegative();

        });
    }
}
