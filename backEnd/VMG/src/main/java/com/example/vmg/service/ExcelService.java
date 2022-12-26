package com.example.vmg.service;

import com.example.vmg.model.Department;
import com.example.vmg.respository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {
    @Autowired
    DepartmentRepository departmentRepository;

    public Department getDartmentByName(String name){
        return departmentRepository.getDepartmentByName(name);
    }
}
