package com.example.AI_Interview_Sys.controller;

import com.example.AI_Interview_Sys.model.User;
import com.example.AI_Interview_Sys.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public Object login(@RequestBody User loginUser) {

        Optional<User> user = userService.login(
                loginUser.getEmail(),
                loginUser.getPassword()
        );

        if (user.isPresent()) {
            return user.get();
        }

        return "Invalid Credentials";
    }
}
