package com.example.vmg.service.impl;

import com.example.vmg.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
//    Optional<User> updateStatusByIdStaff(Long idStaff);

//    Boolean existsByEmail(String email);
}
