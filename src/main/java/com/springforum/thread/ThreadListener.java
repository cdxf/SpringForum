package com.springforum.thread;

import com.springforum.thread.event.onThreadView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
@Slf4j
public class ThreadListener implements InitializingBean {
    @Autowired
    ThreadService threadService;

    public ThreadListener() {
    }

    @Async
    @EventListener
    void handleOnThreadView(onThreadView onThreadView) {
        log.info("onThreadView");
        threadService.increaseView(onThreadView.getThreadId());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("ThreadViewListener created");
    }
}
