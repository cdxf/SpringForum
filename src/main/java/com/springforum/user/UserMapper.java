package com.springforum.user;

import com.springforum.user.dto.UserRegister;
import com.springforum.user.dto.UserSummary;

public class UserMapper {
    public static User userRegisterToUser(UserRegister value) {
        return User.builder()
                .email(value.getEmail())
                .password(value.getPassword())
                .username(value.getUsername())
                .avatar_id(value.getAvatar())
                .build();
    }

    public static UserSummary userToUserSummary(User value) {
        return UserSummary.builder()
                .id(value.getId())
                .comments(value.getComments())
                .createdTime(value.getCreatedTime())
                .username(value.getUsername())
                .avatar(value.getAvatar() == null ? null : value.getAvatar().getId())
                .build();
    }
}