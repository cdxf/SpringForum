package com.example.springforum.service;

import com.example.springforum.entity.User;
import com.example.springforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator[] validator;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Validator[] validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public Boolean isExist(String username) {
        return getByUsername(username).isPresent();
    }
    @Transactional(readOnly = false)
    public Optional<User> add(User user) {
        DataBinder binder = new DataBinder(user);
        binder.addValidators(validator);
        binder.validate();
        if(binder.getBindingResult().getAllErrors().size() > 0) return Optional.empty();
        user.password = passwordEncoder.encode(user.password);
        user.setRole("USER");
        User result = userRepository.save(user);
        System.out.println(result.getClass());
        return Optional.of(new User(result.getUsername(),null,result.getEmail(),result.getRole()));
    }

    public Optional<User> findById(Integer integer) {
        return userRepository.findById(integer);
    }

    public Iterable<User> getTopUsers() {
        Page<User> all = userRepository.findAll(PageRequest.of(0, 100));
        return all;
    }
    @Transactional(readOnly = false)
    public Boolean delete(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) return false;
        else{
            userRepository.delete(user.get());
            return true;
        }
    }

    public long count() {
        return userRepository.count();
    }
}
