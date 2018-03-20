package com.example.springforum.exception;

public class UserNotExistException extends RuntimeException{
    private String username;

    public UserNotExistException(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
