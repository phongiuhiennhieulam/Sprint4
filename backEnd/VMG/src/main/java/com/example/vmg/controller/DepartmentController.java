package com.example.vmg.controller;

import com.example.vmg.form.DepartmentForm;
import com.example.vmg.model.Department;
import com.example.vmg.respository.DepartmentRepository;
import com.example.vmg.service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DepartmentController {
    @Autowired
    private PhongBanService phongBanService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    public List<Department> getListPhongBan(){
        return departmentRepository.findAll();
    }

//    @PostMapping("/department")
//    public ResponseEntity<Void> addNhanVien(@ModelAttribute DepartmentForm departmentForm) {
//        Department department = new Department();
//
//        department.setName(departmentForm.getName());
//        department.setStatus(0);
//    }
    @GetMapping("/department")
    public ResponseEntity<Void> addNhanVien(@ModelAttribute DepartmentForm departmentForm){
        Department department = new Department();

        department.setId(departmentForm.getId());
        department.setName(departmentForm.getName());
        department.setStatus(departmentForm.getStatus());

        phongBanService.saveOrUpDate(department);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
