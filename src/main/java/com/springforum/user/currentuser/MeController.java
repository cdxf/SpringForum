package com.springforum.user.currentuser;

import com.springforum.user.UserMapper;
import com.springforum.user.dto.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {
    @Autowired CurrentUserService currentUserService;

    @RequestMapping("api/users/me")
    @Secured({"ROLE_USER"})
    public UserSummary me() {
        return UserMapper.userToUserSummary(currentUserService.getCurrentUser());
    }

}
