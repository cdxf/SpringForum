package com.springforum.user;

import com.springforum.user.dto.UserRegister;
import com.springforum.user.dto.UserSummary;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User userRegisterToUser(UserRegister value) {
        return User.builder()
                .email(value.getEmail())
                .password(value.getPassword())
                .username(value.getUsername())
                .avatar(value.getAvatar())
                .build();
    }

    public static UserSummary userToUserSummary(User value) {
        return UserSummary.builder()
                .username(value.getUsername())
                .avatar(value.getAvatar())
                .id(value.getId())
                .build();
    }
}