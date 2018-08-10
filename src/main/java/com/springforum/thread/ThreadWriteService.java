package com.springforum.thread;

import com.springforum.thread.dto.ThreadDTO;
import com.springforum.user.UserService;
import lombok.Getter;
import lombok.Setter;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
@Setter
@Getter
public class ThreadWriteService {
    @Autowired private EntityManager entityManager;
    @Autowired private UserService userService;
    @Autowired private ThreadDAO threadDAO;
    @Autowired private PolicyFactory sanitizer;

    public ThreadDTO modifyThread(Integer threadID, String content, Integer userId) {
        content = sanitizer.sanitize(content);
        Thread thread = entityManager.getReference(Thread.class, threadID);
        if (!thread.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException(null);
        }
        thread.setContent(content);
        Thread save = entityManager.merge(thread);
        return ThreadMaper.threadDTOMapper(save);
    }


    public void increaseView(Integer id) {
        threadDAO.increaseView(id);
    }

    public ThreadDTO modifyThread(Integer threadID, String content) {
        return modifyThread(threadID, content, userService.getCurrentUser().getId());
    }

}
