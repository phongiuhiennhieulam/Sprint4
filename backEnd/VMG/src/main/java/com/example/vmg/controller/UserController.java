package com.example.vmg.controller;

import com.example.vmg.model.Role;
import com.example.vmg.model.User;
import com.example.vmg.respository.UserRepository;
import com.example.vmg.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/get-user")
    public User getName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return userService.findByUsername(authentication.getName()).get();
    }

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getList(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String keyWord) {
        return new ResponseEntity<Page<User>>(userService.getByPage(page, pageSize, keyWord), HttpStatus.OK);
    }

    @PutMapping("/add-Role/{id}")
    public ResponseEntity<String> SetRole(@PathVariable Long id, @RequestBody Set<Role> role) {
        User user1 = userRepository.findById(id).get();
        user1.setRoles(role);
        userService.update(id, user1);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/getRole")
    public List<User> getRole() {
        return userService.findAll();
    }


//    @PutMapping("/user-update/{id}")
//    public ResponseEntity<User> UpdatePassWord(@PathVariable("id") Long id, @RequestBody UserForm userForm) {
//        User user = userRepository.findById(id).get();
//
//        System.out.println("aaaaaaaaaaaa: "+user.getPassWord());
//        System.out.println("bbbbbbbbbbb: "+ passwordEncoder.matches(userForm.getPassWord(), user.getPassWord()));
//
//        if (isValidPW(userForm.getPassWord())) {
//            System.out.println("userForm.getPassWord()userForm.getPassWord()userForm.getPassWord(): " + isValidPW(userForm.getPassWord()));
//
//            user.setPassWord(passwordEncoder.encode(userForm.getPassWord()));
//            user.setId(id);
//            this.userService.save(user);
//            return new ResponseEntity<User>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
//        }
//    }



    @PutMapping("/user-update/{id}")
    public ResponseEntity<String>UpdatePassWord(@PathVariable Long id, @RequestBody User user){
        User user1 = userRepository.findById(id).get();

        System.out.println("iiiiiiiiiiiiiii: "+user1);
        if (isValidPW(user.getPassWord())) {
            user1.setPassWord(passwordEncoder.encode(user.getPassWord()));
            userService.update(id, user1);
            userService.save(user1);
        }else {

        }
//        userService.update(id, user1);
        return new ResponseEntity<String>(HttpStatus.OK);}

    public boolean isValidPW(String code) {
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20}$");
        Matcher m = p.matcher(code);
        return m.matches();
    }

}