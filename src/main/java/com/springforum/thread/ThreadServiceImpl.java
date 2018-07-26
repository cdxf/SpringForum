package com.springforum.thread;

import com.springforum.comment.CommentRepository;
import com.springforum.exception.ThreadNotExistException;
import com.springforum.forum.Forum;
import com.springforum.generic.PathItemDTO;
import com.springforum.thread.dto.ThreadBase;
import com.springforum.thread.dto.ThreadWithContent;
import com.springforum.user.User;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ThreadServiceImpl implements ThreadService {
    final String lastestQuery =
            "select thread.*, " +
                    "(select count(*) from comment where thread.id = comment.thread_id) as comments " +
                    "from ( " +
                    "SELECT " +
                    "thread.*," +
                    "forum.id as forum_id, " +
                    "forum.name as forum_name, " +
                    "author.id as author_id, " +
                    "author.username as author_name, " +
                    "author.avatar as author_avatar " +
                    "FROM thread " +
                    "inner join forum on thread.forum_id = forum.id " +
                    "inner join users as author on thread.author_id = author.id " +
                    "ORDER BY last_modified " +
                    "LIMIT ? OFFSET ?) thread";
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private PolicyFactory sanitizer;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ThreadWithContent newThread(String title, String content, Integer forumID, Integer userId) {
        content = sanitizer.sanitize(content);
        title = sanitizer.sanitize(title);
        User user = entityManager.find(User.class, userId);
        Forum forum = entityManager.find(Forum.class, forumID);
        var thread = new Thread(title, content, forum, user);
        entityManager.persist(thread);
        while (forum != null) {
            forum.setThreads(forum.getThreads() + 1);
            forum.setPosts(forum.getPosts() + 1);
            forum.setLatestThread(thread);
            forum = forum.getParent();
        }
        return ThreadMaper.threadsThreadWithContent(thread);
    }

    @Override
    @Transactional
    public List<ThreadWithContent> newMultipleThread(List<String> titles, List<String> contents, Integer forumID, List<Integer> userIds) {
        var result = new ArrayList<Thread>();
        Forum forum = entityManager.find(Forum.class, forumID);
        for (int i = 0; i < titles.size(); i++) {
            User user = entityManager.find(User.class, userIds.get(i));
            var thread = new Thread(titles.get(0), contents.get(i), forum, user);
            entityManager.persist(thread);
            result.add(thread);
        }
        while (forum != null) {
            forum.setThreads(forum.getThreads() + titles.size());
            forum.setPosts(forum.getPosts() + titles.size());
            forum.setLatestThread(result.get(result.size() - 1));
            forum = forum.getParent();
        }

        return result.stream().map(it -> ThreadMaper.threadsThreadWithContent(it)).collect(Collectors.toList());
    }

    @Override
    public Optional<ThreadWithContent> getThread(Integer id) {
        ThreadWithContent thread = threadRepository.getThreadByById(id);
        return Optional.ofNullable(thread);
    }

    @Override
    public Iterable<ThreadBase> getThread(Set<Integer> ids) {
        List<ThreadBase> threadsById = threadRepository.getThreadsById(ids);
        return Optional.ofNullable(threadsById)
                .orElse(List.of());
    }

    @Override
    @Transactional(readOnly = false)
    public void increaseView(Integer id) {
        threadRepository.increaseViews(id);
    }

    @Override
    @Transactional(readOnly = false)
    public ThreadWithContent modifyThread(Integer threadID, String content, Integer userId) {
        content = sanitizer.sanitize(content);
        Thread thread = entityManager.getReference(Thread.class, threadID);
        if (!thread.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException(null);
        }
        thread.setContent(content);
        Thread save = threadRepository.save(thread);
        return ThreadMaper.threadsThreadWithContent(save);
    }

    @Override
    public Page<ThreadWithContent> getThreadsByForum(Integer forumID, Pageable page) {
        Page<ThreadWithContent> threads = Optional
                .ofNullable(threadRepository.getThreadsByForum(forumID, page))
                .orElse(Page.empty()).map(it -> ThreadMaper.threadsThreadWithContent(it));
        return threads;
    }

    @Override
    public Page<ThreadBase> getLastest(Pageable page) {
        Page<ThreadBase> threadBases = threadRepository.getLastestThread(page);
        return threadBases;
    }

    @Override
    public Iterable<ThreadWithContent> getThreadsByAuthor(User author) {
        return threadRepository
                .findThreadsByAuthor(author)
                .stream()
                .map(it -> ThreadMaper.threadsThreadWithContent(it))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<PathItemDTO> getPath(Integer threadID) {
        if (threadID == null) return null;
        Thread thread = threadRepository.findById(threadID).orElseThrow(ThreadNotExistException::new);
        List<PathItemDTO> paths = new ArrayList<>();
        paths.add(PathItemDTO.buildThreadPathItem(thread));
        Forum forum = thread.getForum();
        paths.add(PathItemDTO.buildForumPathItem(forum));
        while ((forum = forum.getParent()) != null) {
            paths.add(PathItemDTO.buildForumPathItem(forum));
        }
        Collections.reverse(paths);
        return paths;
    }

    private void updateCommentCount(Iterable<ThreadWithContent> thread) {
        Set<Integer> collect =
                StreamSupport
                        .stream(thread.spliterator(), false)
                        .map(ThreadWithContent::getId)
                        .collect(Collectors.toSet());
        var commentsMap = commentRepository.countByThreadIds(collect);
        Map<Integer, Integer> countMap = commentsMap.stream().collect(Collectors.toMap(it -> (Integer) it[0], it -> ((Long) it[1]).intValue()));
        thread.forEach(it -> it.comments = countMap.get(it.getId()));
    }

    private void updateCommentCountForThreadBase(Iterable<ThreadBase> thread) {
        Set<Integer> collect =
                StreamSupport
                        .stream(thread.spliterator(), false)
                        .map(ThreadBase::getId)
                        .collect(Collectors.toSet());
        var commentsMap = commentRepository.countByThreadIds(collect);
        Map<Integer, Integer> countMap = commentsMap.stream().collect(Collectors.toMap(it -> (Integer) it[0], it -> ((Long) it[1]).intValue()));
        thread.forEach(it -> it.comments = countMap.get(it.getId()));
    }

    private void updateCommentCount(ThreadBase thread) {
        var comments = commentRepository.countCommentsByThreadId(thread.getId());
        thread.comments = comments;
    }

    private void updateCommentCount(ThreadWithContent thread) {
        var comments = commentRepository.countCommentsByThreadId(thread.getId());
        thread.comments = comments;
    }
}
