package com.example.vmg.controller;

import com.example.vmg.form.DepartmentForm;
import com.example.vmg.model.Department;
import com.example.vmg.respository.DepartmentRepository;
import com.example.vmg.service.DepartmentService;
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
    private DepartmentService departmentService;
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

        departmentService.saveOrUpDate(department);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/department")
    public ResponseEntity<String> addDepartment(@RequestBody DepartmentForm departmentForm){
        Department department = new Department();

        department.setId(departmentForm.getId());
        department.setName(departmentForm.getName());
        //department.setStatus(departmentForm.getStatus());

        departmentService.saveOrUpDate(department);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<String>UpdateDepartment(@PathVariable Long id, @RequestBody Department department){
        Department department1 = departmentRepository.findById(id).get();
        department1.setName(department.getName());
        departmentService.update(id, department1);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
