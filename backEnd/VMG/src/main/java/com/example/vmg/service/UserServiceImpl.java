package com.example.vmg.service;

import com.example.vmg.model.User;
import com.example.vmg.respository.UserRepository;
import com.example.vmg.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired private UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

//    @Override
//    public Boolean existsByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
    public User save(User user){
        return userRepository.save(user);
    }
}
