package com.example.springforum.event;

import org.springframework.context.ApplicationEvent;

public class SampleEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SampleEvent(Object source) {
        super(source);
    }
}
