package com.example.springforum.controller;

import com.example.springforum.entity.Comment;
import com.example.springforum.entity.Thread;
import com.example.springforum.entity.User;
import com.example.springforum.exception.UserNotExistException;
import com.example.springforum.service.CommentService;
import com.example.springforum.service.ThreadService;
import com.example.springforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Objects;

@Controller
public class ThreadController {
    @Autowired
    private ThreadService threadService;
    @Autowired
    private UserService userService;
    @Autowired
    SmartValidator smartValidator;
    @Autowired
    private CommentService commentService;

    @PostMapping("newreply")
    @Secured("ROLE_USER")
    public String newReply(@ModelAttribute("thread") Comment comment, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        User author = userService
                .getByUsername(principal.getName())
                .orElseThrow(() -> new UserNotExistException(principal.getName()));
        comment.setAuthor(author);
        commentService.newComment(comment);
        redirectAttributes.addAttribute("id", comment.thread.getId());
        return "redirect:/showthread";
    }

    @GetMapping(value = "showthread", params = "id")
    public String showThread(@RequestParam Integer id, Model model) {
        Thread thread = threadService.getThread(id).orElseThrow(NullPointerException::new);
        model.addAttribute("thread", thread);
        return "thread/showthread";
    }

    @GetMapping(value = "newthread",params = "id")
    @Secured("ROLE_USER")
    public String create(@RequestParam(value = "id",required = true) Integer id, @ModelAttribute("thread")  Thread thread, Model model){
        Objects.requireNonNull(id);
        model.addAttribute("id",id);
       return "thread/new";
    }
    @PostMapping("newthread")
    @Secured("ROLE_USER")
    public String postCreate(Principal principal, Integer id, @ModelAttribute("thread") Thread thread, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("id",id);
        User user = userService.getByUsername(principal.getName()).get();
        System.out.println("From Create" + thread.content);
        threadService.create(thread.title,thread.content,id,user);
        System.out.println(thread.content);
    return "redirect:/forumdisplay";
    }
}
