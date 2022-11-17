package com.example.vmg.service.impl;

import com.example.vmg.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

//    Boolean existsByEmail(String email);
}
