package com.example.vmg.service.impl;

import com.example.vmg.model.ERole;
import com.example.vmg.model.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(ERole name);

}
