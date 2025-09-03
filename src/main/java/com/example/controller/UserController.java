package com.example.controller;

import com.example.entity.UserEntity;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //get all
    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return userService.findAllUsers();
    }

    //add user
    @PostMapping("/users")
    public UserEntity addUser(@RequestBody UserEntity user) {
        return userService.saveUser(user);
    }

}
