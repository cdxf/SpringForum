package com.example.springforum.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher implements InitializingBean {
    @Autowired
    ApplicationContext ctx;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Đéo vui đâu thật đó");
        applicationEventPublisher.publishEvent(new ContextRefreshedEvent(ctx));
    }
}
