package com.example.vmg.service;

import com.example.vmg.model.Department;
import com.example.vmg.respository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhongBanService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getList(){
        return departmentRepository.findAll();
    }

    public Department getById(Long id){
        return departmentRepository.findById(id).get();
    }

    public void saveOrUpDate(Department department){
        departmentRepository.save(department);
    }

    public void delete(Long id){
        departmentRepository.deleteById(id);
    }
}
