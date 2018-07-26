package com.springforum.generic;

import com.springforum.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @Autowired
    UserService userService;

    @InitBinder
    public void binder(WebDataBinder binder) {
        //binder.addValidators(userValidator);
    }
}
