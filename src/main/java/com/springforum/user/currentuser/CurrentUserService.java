package com.springforum.user.currentuser;

import com.springforum.user.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            var auth = (UsernamePasswordAuthenticationToken) authentication;
            return (User) auth.getPrincipal();
        }
        throw new BadCredentialsException(authentication.getClass().getName());
    }
}
