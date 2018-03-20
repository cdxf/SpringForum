package com.example.springforum.event;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContextRefresh {
    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("Contexts Refreshed Event");
        System.out.println(event);
        System.out.println("WTFs");
        List<?> list = new ArrayList<String>();
        list.add(null);
        list.clear();
    }
    @EventListener(AuthenticationFailureBadCredentialsEvent.class)
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        System.out.println("Dang nhap sai");
    }
}
