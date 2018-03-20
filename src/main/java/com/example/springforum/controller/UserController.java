package com.example.springforum.controller;


import com.example.springforum.entity.Thread;
import com.example.springforum.entity.User;
import com.example.springforum.service.ThreadService;
import com.example.springforum.service.UserService;
import com.example.springforum.spring.UserDetailImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    ThreadService threadService;
    @Autowired
    UserDetailImp userDetailImp;
    @Autowired
    private UserService userService;

    @RequestMapping("/profile")
    @Secured("ROLE_USER")
    public String profile(Principal auth, Model model) {
        System.out.println("Hello Wofsarld");
        User user = userService.getByUsername(auth.getName()).get();
        model.addAttribute("user", user);
        return "/users/profile";
    }
    @RequestMapping("/hehe")
    @ResponseBody
    public Object hehe(Authentication auth, Model model) {
        System.out.println(auth.getDetails());
        return (User)auth.getPrincipal();
    }
    @RequestMapping("/threads")
    @Secured("ROLE_USER")
    @ResponseBody
    public Iterable<Thread> threads(@RequestParam() String authorName) {
        return threadService.findThreadsByAuthor(authorName);
    }

    @RequestMapping
    public String index(@ModelAttribute("user_count") Long user_count, Model model) {
        return "users/index";
    }

    @GetMapping("/register")
    public String register(User user) {
        System.out.println(user.username);
        return "users/register";

    }

    @GetMapping("/add")
    public String add(User user) {
        System.out.println(user.username);
        return "users/register";

    }


    @PostMapping("/register")
    public ModelAndView checkRegisterForm(@Valid User bindedUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/users/register");
        }
        if (userService.isExist(bindedUser.username)) {
            ModelAndView modelAndView = new ModelAndView("/users/register");
            bindingResult.addError(new FieldError("user", "username", "Username already existed"));
            return modelAndView;
        }
        Optional<User> optionalUser = userService.add(bindedUser);
        User user = optionalUser.orElseThrow(IllegalStateException::new);
        UserDetails userDetails = userDetailImp.loadUserByUsername(user.username);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user.username, null, user.getAuthorities());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ModelAndView("redirect:/users/profile");
    }
}
