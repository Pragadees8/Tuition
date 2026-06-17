package com.tutor.service;

import com.tutor.entity.User;
import com.tutor.enums.Role;

import java.util.Optional;

public interface UserService {

    User registerUser(String name,
                      String email,
                      String password,
                      Role role);

    Optional<User> loginUser(String email,
                             String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}