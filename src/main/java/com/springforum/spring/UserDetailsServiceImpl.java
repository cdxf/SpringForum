package com.springforum.spring;

import com.springforum.user.User;
import com.springforum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return user;
    }
}
