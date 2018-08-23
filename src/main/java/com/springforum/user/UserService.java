package com.springforum.user;

import com.springforum.Avatar;
import com.springforum.avatar.AvatarService;
import com.springforum.user.dto.UserRegister;
import com.springforum.user.dto.UserSummary;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
@Transactional(readOnly = true)
@Getter
@Setter
public class UserService {
    @Autowired private EntityManager entityManager;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private AvatarService avatarService;

    public Optional<User> getByUsername(String username) {
        User user = null;
        try {
            user = entityManager.createQuery("select u from User u where u.username = ?1", User.class)
                    .setParameter(1, username).getSingleResult();
        } catch (NoResultException e) {
        }
        Optional<User> byUsername = Optional.ofNullable(user);
        return byUsername;
    }

    public boolean isEmailExisted(String email) {
        List<User> resultList = entityManager.createQuery("select u from User u where u.email = ?1", User.class)
                .setParameter(1, email).getResultList();
        return resultList.size() > 0;
    }

    public Optional<User> getById(Integer id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            var auth = (UsernamePasswordAuthenticationToken) authentication;
            return (User) auth.getPrincipal();
        }
        throw new BadCredentialsException(authentication.getClass().getName());
    }

    public Boolean isExist(String username) {
        return getByUsername(username).isPresent();
    }

    @Transactional
    public UserSummary newUser(@Valid UserRegister userRegister) {
        Avatar reference = entityManager.getReference(Avatar.class, userRegister.getAvatar());
        User user = UserMapper.userRegisterToUser(userRegister);
        user.setComments(0);
        user.setThreads(0);
        user.setAvatar(reference);
        user.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        user.setRole(List.of(User.ROLE.USER));
        UserActivity userActivity = new UserActivity();
        userActivity.setLastThreadCreation(Instant.ofEpochMilli(0));
        userActivity.setUser(user);
        User save = entityManager.merge(user);
        return UserMapper.userToUserSummary(save);
    }


    @Transactional(readOnly = false)
    public Boolean setRole(Integer userID, List<User.ROLE> role) {
        return getById(userID)
                .map(user -> {
                    user.setRole(role);
                    entityManager.persist(user);
                    return true;
                })
                .orElse(false);
    }

    ;

    @Transactional(readOnly = false)
    public Boolean banUser(Integer userID) {
        return setRole(userID, List.of(User.ROLE.BANNED));
    }


}
