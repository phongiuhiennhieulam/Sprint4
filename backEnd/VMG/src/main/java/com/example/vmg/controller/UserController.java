package com.example.vmg.controller;

import com.example.vmg.model.Role;
import com.example.vmg.model.Staff;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired private UserServiceImpl userService;
    @Autowired private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/get-user")
    public List<User>  getName(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return userService.findAll();
    }
    @GetMapping("/users")
    public ResponseEntity<Page<User>> getList(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<Page<User>>(userService.getByPage(page, pageSize), HttpStatus.OK);
    }
    @PutMapping ("/add-Role/{id}")
    public ResponseEntity<String> AddRole(@PathVariable Long id, @RequestBody Set<Role> role){
        User user1 = userRepository.findById(id).get();
        user1.setRoles(role);
        userService.update(id,user1);
        return new ResponseEntity<String>(HttpStatus.OK);

    }

    @GetMapping("/getRole")
    public List<User> getRole(){
        return userService.findAll();
    }

//    @GetMapping("/get-user")
//    public ResponseEntity<Page<Staff>> getList(@RequestParam(defaultValue = "0") int page
//            , @RequestParam(defaultValue = "10") int pageSize){
//        return new ResponseEntity<Page<Staff>>( userService.getByPage(page, pageSize), HttpStatus.OK);
//    }



    @PutMapping("/user-delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping("/user-unlock/{id}")
    public ResponseEntity<String> unLockStaff(@PathVariable Long id){
        userService.unLock(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping("/delete-Role/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id, @RequestBody User user){
        User user1 = userRepository.findById(id).get();
        user1.setRoles(user.getRoles());
        userService.delete(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
