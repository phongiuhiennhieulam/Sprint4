package com.example.vmg.controller;

import com.example.vmg.model.Staff;
import com.example.vmg.model.StatisticalInterface;
import com.example.vmg.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class StatisticalController {
    @Autowired
    private StaffService staffService;
    @GetMapping("/staffs-by-welfare/{id}")
    public ResponseEntity<List<StatisticalInterface>> getList(@PathVariable Long id){
        return new ResponseEntity<List<StatisticalInterface>>(staffService.getStaffByWelfare(id), HttpStatus.OK);
    }
}
