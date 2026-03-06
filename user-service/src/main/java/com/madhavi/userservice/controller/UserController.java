package com.madhavi.userservice.controller;

import com.madhavi.userservice.model.User;
import com.madhavi.userservice.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
