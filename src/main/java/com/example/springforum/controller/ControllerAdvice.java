package com.example.springforum.controller;

import com.example.springforum.entity.SiteDetail;
import com.example.springforum.exception.UserNotExistException;
import com.example.springforum.service.SiteDetailService;
import com.example.springforum.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @Autowired
    UserService userService;
    @Autowired
    private SiteDetailService siteDetailsService;

    @ExceptionHandler({NullPointerException.class,MissingServletRequestParameterException.class})
    public String handle(){
        LoggerFactory.getLogger(this.getClass()).info("Handled");
        return "/error";
    }
    @ExceptionHandler(UserNotExistException.class)
    public String userNotExistExceptionHandler(UserNotExistException exception, Model model){
        model.addAttribute("username",exception.getUsername());
        return "error/userNotExist";
    }
    @InitBinder
    public void binder(WebDataBinder binder) {
        //binder.addValidators(userValidator);
    }
    @ModelAttribute("siteDetails")
    public Map<String,SiteDetail> siteDetails(){
        return siteDetailsService.getAllSiteDetails();
    }
}
