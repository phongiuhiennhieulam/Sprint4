package com.example.vmg.service;

import com.example.vmg.model.User;
import com.example.vmg.respository.UserRepository;
import com.example.vmg.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
public User update(Long id, User user) {
    return userRepository.save(user);
}
    public User save(User user){
        return userRepository.save(user);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public void delete(Long id){
        User user = userRepository.findById(id).get();
        user.setStatus(1);
        userRepository.save(user);
    }
    public void unLock(Long id){
        User user =  userRepository.findById(id).get();
        user.setStatus(0);
        userRepository.save(user);
    }

    public Page<User> getByPage(int pageNumber, int maxNumber, String keyWord){
        Pageable pageable = PageRequest.of(pageNumber, maxNumber);
        keyWord = "%" + keyWord + "%";
        return userRepository.getPage(pageable,keyWord);
    }


    public void looks(List<String> emails) {
        userRepository.looks(emails);
    }
}
