package com.springforum.thread.event;

import com.springforum.thread.ThreadWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static java.lang.System.currentTimeMillis;

@Component
@Lazy(false)
@Slf4j
public class ThreadListener implements InitializingBean {
    @Autowired
    ThreadWriteService threadWriteService;

    public ThreadListener() {
    }

    @Async
    @EventListener
    void handleOnThreadView(onThreadView onThreadView) {
        var now = currentTimeMillis();
        threadWriteService.increaseView(onThreadView.getThreadId());
        log.info("onThreadView: {} in {} ms", Thread.currentThread().getName(), currentTimeMillis() - now);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("ThreadViewListener created");
    }
}
