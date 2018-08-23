package com.springforum.thread;

import com.springforum.generic.TimestampKeyset;
import com.springforum.thread.dto.ThreadDTO;
import com.springforum.thread.event.onThreadView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
@Setter
@Getter
public class ThreadReadService {
    private ThreadDAO threadDAO;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ThreadReadService(ThreadDAO threadDAO, ApplicationEventPublisher applicationEventPublisher) {
        this.threadDAO = threadDAO;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ThreadDTO getThread(Integer id) {
        applicationEventPublisher.publishEvent(new onThreadView(this, id));
        return threadDAO.findById(id);
    }


    public List<ThreadDTO> getThreadsByForum(Integer forumID, TimestampKeyset keyset) {
        return threadDAO.getThreadsByForum(forumID, keyset);
    }


    public List<ThreadDTO> getByUserID(Integer userId, TimestampKeyset timestampKeyset) {
        List<ThreadDTO> result = threadDAO.findByUserId(userId, timestampKeyset);
        return result;
    }

    public List<Instant> getRange(Integer forumID) {
        return threadDAO.getRange(forumID);
    }
}
