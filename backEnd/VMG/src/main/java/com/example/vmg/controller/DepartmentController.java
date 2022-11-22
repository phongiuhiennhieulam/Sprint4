package com.example.vmg.controller;

import com.example.vmg.dto.respose.MessageResponse;
import com.example.vmg.form.DepartmentForm;
import com.example.vmg.model.Department;
import com.example.vmg.respository.DepartmentRepository;
import com.example.vmg.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<Department> getListPhongBan() {
        return departmentRepository.findAll();
    }

    @GetMapping("/department")
    public ResponseEntity<Void> addNhanVien(@ModelAttribute DepartmentForm departmentForm) {
        Department department = new Department();
        department.setId(departmentForm.getId());
        department.setName(departmentForm.getName());


        departmentService.saveOrUpDate(department);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/department")
    public ResponseEntity<String> addDepartment(@RequestBody Department department) {
        try {
            if (departmentService.existsByName(department.getName().trim())) {

                return ResponseEntity.badRequest().body(new String("Phòng ban đã tồn tại"));
            }
            if (department.getName().trim() != null && department.getName().trim() != "") {
                Department department1 = new Department();
                department1.setId(department.getId());
                department1.setName(department.getName());
                departmentService.saveOrUpDate(department1);
                return new ResponseEntity<String>(HttpStatus.OK);
            } else {
                return ResponseEntity.badRequest().body(new String("Phòng ban không được phép để trống"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new String("Erorr"));
        }
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> UpdateDepartment(@PathVariable Long id, @RequestBody DepartmentForm departmentForm) {
        if (departmentForm.getName().trim() != null && departmentForm.getName().trim() != "") {
            Department department1 = departmentRepository.findById(id).get();
            department1.setName(departmentForm.getName());
            departmentService.update(id, department1);
            return new ResponseEntity<String>(HttpStatus.OK);

        } else {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/departmentss")
    public ResponseEntity<Page<Department>> getList(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String keyWord) {
        return new ResponseEntity<Page<Department>>(departmentService.getByPage(page, pageSize, keyWord), HttpStatus.OK);
    }


}
