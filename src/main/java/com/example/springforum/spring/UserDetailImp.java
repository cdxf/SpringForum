package com.example.springforum.spring;

import com.example.springforum.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImp implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userService.getByUsername(username);
        return user.map(it -> org.springframework.security.core.userdetails.User
                .builder()
                .username(it.getUsername())
                .password(it.getPassword())
                .roles(it.getRole()).build()
        ).orElseThrow(()->new UsernameNotFoundException("Username Not Found"));
    }
}
