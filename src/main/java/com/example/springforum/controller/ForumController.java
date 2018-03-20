package com.example.springforum.controller;

import com.example.springforum.exception.ForumNotExistException;
import com.example.springforum.service.ForumService;
import com.example.springforum.service.ThreadService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForumController {

    public static final String FORUM_DISPLAY = "forum/forumdisplay";
    @Autowired
    private ForumService forumService;
    @Autowired
    private ThreadService threadService;
    @RequestMapping("forumdisplay")
    public String rootForum(Model model) {
        val subforums = forumService.getRootSubForum();
        model.addAttribute("bags",forumService.getRootForumSummary());
        return FORUM_DISPLAY;
    }

    @RequestMapping(value = "/forumdisplay", params = "id")
    public String displayForum(Model model, @RequestParam(value = "id", required = true) Integer id) {
        val optionalForum = forumService.getForum(id);
        if (!optionalForum.isPresent()) throw new ForumNotExistException();
        val bags = forumService.getForumSummary(id);
        model.addAttribute("bags",bags);
        return FORUM_DISPLAY;
    }
}
