package com.example.vmg.controller;

import com.example.vmg.model.RegisterWelfare;
import com.example.vmg.model.User;
import com.example.vmg.model.Welfare;
import com.example.vmg.service.UserServiceImpl;
import org.apache.coyote.http11.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired private UserServiceImpl userService;

    @GetMapping("/get-user")
    public User getName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return userService.findByUsername(authentication.getName()).get();
    }
}
