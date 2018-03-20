package com.example.springforum.controller;

import com.example.springforum.service.SiteDetailService;
import com.example.springforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));
        if(isUser) return "redirect:/";
        else return "login";
    }
    @RequestMapping("/redirecttest")
    public String hehe(RedirectAttributes e) {
        e.addAttribute("title", "Hihi");
        return "redirect:/test";
    }

    @Autowired
    SiteDetailService siteDetailsService;

    @RequestMapping("/users/fun")
    public void test(@RequestParam(required = false) String title) {


    }

    @RequestMapping("/")
    public String index() {
        return "forward:/forumdisplay";
    }

}

