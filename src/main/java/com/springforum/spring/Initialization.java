package com.springforum.spring;

import com.github.javafaker.Faker;
import com.springforum.avatar.AvatarService;
import com.springforum.comment.create_comment.CreateCommentService;
import com.springforum.forum.Forum;
import com.springforum.forum.ForumService;
import com.springforum.forum.create_new_forum.CreateForumService;
import com.springforum.thread.ThreadWriteService;
import com.springforum.thread.create_new_thread.CreateThreadService;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.User;
import com.springforum.user.UserService;
import com.springforum.user.dto.UserRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@Slf4j
public class Initialization implements InitializingBean {
    @Autowired
    UserService userService;
    @Autowired
    ForumService forumService;
    @Autowired
    CreateForumService createForumService;
    @Autowired
    ThreadWriteService threadWriteService;
    @Autowired
    CreateThreadService createThreadService;
    @Autowired
    CreateCommentService commentService;
    @Autowired
    AvatarService avatarService;
    @Value("${springforum.sampleDB}")
    Boolean sampleDB;
    Random rand = new Random(System.currentTimeMillis());

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!sampleDB)
            return;
        //for jar file
        FileSystemResource fileSystemResource = new FileSystemResource("static/avatar");
        var files = fileSystemResource.getFile().listFiles();
        var size = files == null ? 0 : files.length;
        InputStream[] fileInputStreams = new InputStream[size];
        for (int i = 0; i < size; i++) {
            fileInputStreams[i] = new FileInputStream(files[i]);
        }
        // for classpth
        var pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        var resources = pathMatchingResourcePatternResolver.getResources("static/avatar/**");
        var resources2 = pathMatchingResourcePatternResolver.getResources("static/avatar/**");
        var resourcesAll = ArrayUtils.addAll(resources, resources2);
        InputStream[] classPathInputStream = new InputStream[resourcesAll.length];
        for (int i = 0; i < resourcesAll.length; i++) {
            classPathInputStream[i] = resourcesAll[i].getInputStream();
        }
        var inputStreams = ArrayUtils.addAll(fileInputStreams, classPathInputStream);
        List<Integer> avatars = new ArrayList<>();
        if (inputStreams.length == 0) throw new IllegalArgumentException("Can't found the avatar");
        for (var file : inputStreams) {
            try {
                Integer integer = avatarService.addAvatar(file.readAllBytes());
                avatars.add(integer);
            } catch (Exception e) {
                log.error("Can't read avatar file");
            }
        }
        var username = "Anonymous";
        try {
            User user = null;
            Optional<User> getUser = userService.getByUsername(username);
            if (!getUser.isPresent()) {
                UserRegister userRegister = UserRegister.builder().username(username).password("Anonymous")
                        .email("anonymous@gmail.com").avatar(avatars.get(0)).build();
                userService.newUser(userRegister);
                log.info("Saved User");
            }
            user = userService.getByUsername(username).get();
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        Faker faker = new Faker();
        // Random users
        List<Integer> userIDs = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    UserRegister userRegister = UserRegister.builder().username(faker.lorem().characters(20))
                            .password("123456789").avatar(avatars.get(rand.nextInt(avatars.size())))
                            .email(faker.lorem().characters(33).replace(" ", "") + "@gmail.com").build();
                    userIDs.add(userService.newUser(userRegister).getId());
                }
            }));
        }
        threadList.forEach(thread -> thread.start());
        for (Thread t : threadList) {
            t.join();
        }
        if (forumService.getForums().iterator().hasNext())
            return;

        for (int x = 0; x < rand.nextInt(5) + 5; x++) {
            Forum root = createForumService.newForum(faker.book().genre(), faker.cat().name(), null).orElseThrow();
            for (int j = 0; j < rand.nextInt(5) + 3; j++) {
                Forum add = createForumService.newForum(faker.book().publisher(), faker.book().author(), root.getId())
                        .orElseThrow();
                ThreadDTO thread = createThreadService.newThread(faker.lorem().sentence(rand.nextInt(10) + 10),
                        faker.lorem().paragraph(20), root.getId(), userIDs.get(rand.nextInt(userIDs.size())));
                var contents1 = new ArrayList<String>();
                var userIDs1 = new ArrayList<Integer>();
                var titleForThreads = new ArrayList<String>();
                var contentForThreads = new ArrayList<String>();
                var userIdForThreads = new ArrayList<Integer>();
                var forum = add.getId();
                for (int k = 0; k < rand.nextInt(20) + 5; k++) {
                    if (Math.random() < 0.05) {
                        Forum subforum = createForumService
                                .newForum(faker.book().publisher(), faker.book().author(), add.getId()).orElseThrow();
                    }
                    titleForThreads.add(faker.lorem().sentence(rand.nextInt(10) + 10));
                    contentForThreads.add(faker.lorem().paragraph(rand.nextInt(100) + 2));
                    userIdForThreads.add(userIDs.get(rand.nextInt(userIDs.size())));
                }
                List<ThreadDTO> threadWithContents = createThreadService.newMultipleThread(titleForThreads,
                        contentForThreads, forum, userIdForThreads);
                for (int k = 0; k < threadWithContents.size(); k++) {
                    var contents2 = new ArrayList<String>();
                    var userIDs2 = new ArrayList<Integer>();
                    for (int y = 0; y < rand.nextInt(20) + 5; y++) {
                        contents1.add(faker.lorem().paragraph(rand.nextInt(100) + 1));
                        contents2.add(faker.lorem().paragraph(rand.nextInt(100) + 1));
                        userIDs1.add(userIDs.get(rand.nextInt(userIDs.size())));
                        userIDs2.add(userIDs.get(rand.nextInt(userIDs.size())));
                    }
                    commentService.createMultipleComments(threadWithContents.get(k).getId(), contents2, userIDs2);
                }
                titleForThreads.clear();
                contentForThreads.clear();
                userIdForThreads.clear();
                for (int k = 0; k < rand.nextInt(25) + 10; k++) {
                    titleForThreads.add(faker.lorem().sentence(rand.nextInt(10) + 10));
                    contentForThreads.add(faker.lorem().paragraph(rand.nextInt(100) + 2));
                    userIdForThreads.add(userIDs.get(rand.nextInt(userIDs.size())));
                }
                createThreadService.newMultipleThread(titleForThreads, contentForThreads, forum, userIdForThreads);
                commentService.createMultipleComments(thread.getId(), contents1, userIDs1);
            }
        }
        log.info("Saved Forum");
    }
}
