package com.springforum.user;

import com.springforum.generic.BindingErrorDTO;
import com.springforum.user.dto.UserRegister;
import com.springforum.user.dto.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/users")
public class UserAPI {
    @Autowired
    private UserService userService;

    @RequestMapping("/me")
    @Secured({"ROLE_USER"})
    public User me() {
        return userService.getCurrentUser();
    }

    @GetMapping(params = "id")
    public Iterable<UserSummary> users(@RequestParam("id") Set<Integer> ids) {
        return userService.findAllById(ids);
    }

    @RequestMapping("/mee")
    public User test(Principal principle) {
        return userService.getCurrentUser();
    }

    @RequestMapping("/register")
    public Object register(@Valid UserRegister user, BindingResult error) {
        var result = new HashMap<>();
        if (error.hasErrors()) {
            System.out.println("Error");
            Stream<BindingErrorDTO> bindingErrorDTOStream = error.getAllErrors().stream()
                    .filter(it -> (it instanceof FieldError))
                    .map(it -> (FieldError) it)
                    .map(field -> new BindingErrorDTO(field.getField(), field.getDefaultMessage()));
            var errors = new RegisterErrorsDTO(bindingErrorDTOStream);
            result.put("error", errors.getErrors());
        } else {
            try {
                UserSummary createdUser = userService.newUser(user);
                result.put("result", createdUser);
            } catch (Exception e) {
                result.put("error", e.getMessage());
            }
        }
        return result;
    }
}
