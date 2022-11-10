package com.example.vmg.service;

import com.example.vmg.model.Department;
import com.example.vmg.respository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
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
    }public void update(Long id, Department department) {
        departmentRepository.save(department);
    }

    public Page<Department> getByPage(int pageNumber, int maxNumber, String keyWord) {
        Pageable pageable = PageRequest.of(pageNumber, maxNumber);
        keyWord = "%" + keyWord + "%";
        return departmentRepository.getPage(pageable,keyWord);
    }

}