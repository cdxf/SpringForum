package com.example.springforum.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
    public class SampleEventListener implements ApplicationListener<SampleEvent> {
    @Override
    public void onApplicationEvent(SampleEvent event) {
        System.out.println(event.getSource());
    }
}


@Component
class SampleEventListeners {
    @TransactionalEventListener
    public void onApplicationEvent(RuntimeException event) {
        System.out.println("recorded");
    }
}
