package com.springforum.spring;

import com.github.javafaker.Faker;
import com.springforum.comment.CommentService;
import com.springforum.forum.Forum;
import com.springforum.forum.ForumService;
import com.springforum.thread.ThreadService;
import com.springforum.thread.dto.ThreadWithContent;
import com.springforum.user.User;
import com.springforum.user.UserService;
import com.springforum.user.dto.UserRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@Slf4j
public class Initialization implements InitializingBean {
    private final UserService userService;
    private final ForumService forumService;
    private final ThreadService threadService;
    private final CommentService commentService;
    @Value("${springforum.sampleDB}")
    Boolean sampleDB;

    @Autowired
    public Initialization(UserService userService, ForumService forumService, ThreadService threadService, CommentService commentService) {
        this.userService = userService;
        this.forumService = forumService;
        this.threadService = threadService;
        this.commentService = commentService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!sampleDB) return;
        var username = "snoob";
        User user = null;
        try {
            Optional<User> getUser = userService.getByUsername(username);
            if (!getUser.isPresent()) {
                UserRegister userRegister = UserRegister.builder()
                        .username(username)
                        .password("123456789")
                        .email("snoobvn@gmail.com")
                        .avatar("Simpsons_-_Grampa.jpg")
                        .build();
                userService.newUser(userRegister);
                log.info("Saved User");
            }
            user = userService.getByUsername(username).get();
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        Faker faker = new Faker();
        //Random users
        List<Integer> userIDs = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 125; j++) {
                    UserRegister userRegister = UserRegister.builder()
                            .username(faker.lorem().characters(20))
                            .password("123456789")
                            .email(faker.lorem().characters(33).replace(" ", "") + "@gmail.com")
                            .avatar("Simpsons_-_Grampa.jpg")
                            .build();
                    userIDs.add(userService.newUser(userRegister).getId());
                }
            }));
        }
        threadList.forEach(thread -> thread.start());
        for (Thread t : threadList) {
            t.join();
        }
        if (forumService.getRootForum().iterator().hasNext()) return;
        var rand = new Random(System.currentTimeMillis());
        for (int x = 0; x < rand.nextInt(5) + 5; x++) {
            Forum root = forumService
                    .add(faker.book().genre(), faker.cat().name(), null).orElseThrow();
            for (int j = 0; j < rand.nextInt(5) + 3; j++) {
                Forum add = forumService
                        .add(faker.book().publisher(), faker.book().author(), root.getId())
                        .orElseThrow();
                ThreadWithContent thread = threadService.newThread(faker.lorem().sentence(rand.nextInt(10) + 10), faker.lorem().paragraph(20), root.getId(), userIDs.get(rand.nextInt(999) + 1));
                var contents1 = new ArrayList<String>();
                var userIDs1 = new ArrayList<Integer>();
                var titleForThreads = new ArrayList<String>();
                var contentForThreads = new ArrayList<String>();
                var userIdForThreads = new ArrayList<Integer>();
                var forum = add.getId();
                for (int k = 0; k < rand.nextInt(200) + 5; k++) {
                    titleForThreads.add(faker.lorem().sentence(rand.nextInt(10) + 10));
                    contentForThreads.add(faker.lorem().paragraph(20));
                    userIdForThreads.add(userIDs.get(rand.nextInt(userIDs.size())));
                }
                List<ThreadWithContent> threadWithContents = threadService.newMultipleThread(titleForThreads, contentForThreads, forum, userIdForThreads);
                for (int k = 0; k < threadWithContents.size(); k++) {
                    var contents2 = new ArrayList<String>();
                    var userIDs2 = new ArrayList<Integer>();
                    for (int y = 0; y < rand.nextInt(50) + 5; y++) {
                        contents1.add(faker.lorem().paragraph(20));
                        contents2.add(faker.lorem().paragraph(20));
                        userIDs1.add(userIDs.get(rand.nextInt(userIDs.size())));
                        userIDs2.add(userIDs.get(rand.nextInt(userIDs.size())));
                    }
                    commentService.postMultipleComment(threadWithContents.get(k).getId(), contents2, userIDs2);
                }
                commentService.postMultipleComment(thread.getId(), contents1, userIDs1);
            }
        }
        log.info("Saved Forum");
    }
}
