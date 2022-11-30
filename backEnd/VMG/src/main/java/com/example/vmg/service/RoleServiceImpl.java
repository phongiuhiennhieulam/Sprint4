package com.example.vmg.service;

import com.example.vmg.model.ERole;
import com.example.vmg.model.Role;
import com.example.vmg.respository.RoleRepository;
import com.example.vmg.service.impl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
