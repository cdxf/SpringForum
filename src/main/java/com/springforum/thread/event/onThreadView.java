package com.springforum.thread.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class onThreadView extends ApplicationEvent {
    private Integer threadId;

    public onThreadView(Object source, Integer threadId) {
        super(source);
        this.threadId = threadId;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public onThreadView(Object source) {
        super(source);
    }
}
