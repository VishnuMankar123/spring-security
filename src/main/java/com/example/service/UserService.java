package com.example.service;

import com.example.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    //save User
    public UserEntity saveUser(UserEntity user);

    //find User by username
    public UserEntity findByUsername(String username);

    //find User by id
    public UserEntity findById(Long id);

    //find User by role
    public List<UserEntity> findByRole(String role);

    //find all Users
    public List<UserEntity> findAllUsers();
}
