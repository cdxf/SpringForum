package com.example.springforum.spring;

import com.example.springforum.entity.Forum;
import com.example.springforum.entity.SiteDetail;
import com.example.springforum.entity.User;
import com.example.springforum.service.ForumService;
import com.example.springforum.service.SiteDetailService;
import com.example.springforum.service.UserService;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Initialization {
    private final UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ForumService forumService;
    private final SiteDetailService siteDetailsService;
    @Autowired
    public Initialization(UserService userService, ForumService forumService, SiteDetailService siteDetailsService) {
        this.userService = userService;
        this.forumService = forumService;
        this.siteDetailsService = siteDetailsService;
        System.out.println("ddsf");
        init();
    }
    public void init() {
        System.out.println(siteDetailsService.getKey("sitename"));
        try {
            val add = userService.add(new User("s", "123456789", "snoobvn@gmail.com", "USER"));
            logger.info("Saved User");
        }catch (DataAccessException e){
            System.out.println(e.getMessage());
            System.out.println("WTF");
        }
        Forum root = forumService.add("Test","Description",null);
        Forum add = forumService.add("Hihih", "Afsafsafasfsa", root.getId());
        System.out.println(add.getClass());
        logger.info("Saved Forum");
        Optional<SiteDetail> sitename = siteDetailsService.getKey("sitename");
        //if(!sitename.isPresent()){
            siteDetailsService.setValue("sitename","Hohoho");
            siteDetailsService.setDescription("sitename","WTFBros");
        //}
        logger.info("Saved SiteDetails");
    }
}
