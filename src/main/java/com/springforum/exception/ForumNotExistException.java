package com.springforum.exception;

public class ForumNotExistException extends RuntimeException{
    private static final long serialVersionUID = -7545721241023276973L;

    public ForumNotExistException(String message) {
        super(message);
    }
}
