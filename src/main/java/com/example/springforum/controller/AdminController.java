package com.example.springforum.controller;

import com.example.springforum.entity.User;
import com.example.springforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    javax.validation.Validator validator;
    @Autowired
    UserService userService;
    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("users",userService.getTopUsers());
        return "admin/index";
    }
    @PostMapping("/users/delete")
    public String delete(@RequestParam Integer id, @RequestHeader("Referer") String referer){
        userService.delete(id);
        return "redirect:" + referer;
    }
    @PostMapping("/user/add")
    public String add(@Valid User user, BindingResult result, Boolean isAdmin, @RequestHeader("Referer") String referer){
        if(isAdmin != null) user.role = "ADMIN";
        System.out.println(result.hasErrors());
        if(!result.hasErrors()) userService.add(user);
        return "redirect:" + referer;
    }
}
