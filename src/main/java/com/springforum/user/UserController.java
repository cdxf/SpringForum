package com.springforum.user;

import com.springforum.user.dto.UserRegister;
import com.springforum.user.dto.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "users", params = "id")
    public Iterable<UserSummary> users(@RequestParam("id") Set<Integer> ids) {
        return null;
    }

    @PostMapping("users")
    public Object register(@Valid UserRegister user) {
        return userService.newUser(user);
    }
}
