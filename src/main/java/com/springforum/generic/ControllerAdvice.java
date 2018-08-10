package com.springforum.generic;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice implements ProblemHandling {
    @InitBinder
    public void binder(WebDataBinder binder) {
        //binder.addValidators(userValidator);
    }
}
