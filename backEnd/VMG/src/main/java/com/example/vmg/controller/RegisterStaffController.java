package com.example.vmg.controller;

import com.example.vmg.dto.respose.MessageResponse;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.model.Staff;
import com.example.vmg.model.WelfareStaffEntity;
import com.example.vmg.respository.StaffRepository;
import com.example.vmg.service.RegisterStaffService;
import com.example.vmg.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RegisterStaffController {
    @Autowired private StaffService staffService;
    @Autowired private RegisterStaffService registerStaffService;

//    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/list-register")
    public ResponseEntity<Page<Staff>> getListRegister(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "9") int pageSize,@RequestParam(defaultValue = "") String keyWord){
        if (!(keyWord.equals("undefined") || keyWord == null)) {
            keyWord = keyWord.trim();
            return new ResponseEntity<Page<Staff>>(registerStaffService.getRegister(page, pageSize,keyWord), HttpStatus.OK);
        } else {
            return null;
        }
    }

    @PutMapping("/accept-register/{id}")
    public ResponseEntity <?> AcceptRegisters(@PathVariable Long id){
        try {
            Staff staff = staffService.getById(id);
            staff.setStatus(0);
            staffService.update(id, staff);
            return ResponseEntity.ok(new MessageResponse("successfully!"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("erorr!"));
        }
    }

    @PutMapping("/cancel-register/{id}")
    public ResponseEntity <?> CancelRegisters(@PathVariable Long id){
        try {
            Staff staff = staffService.getById(id);
            staff.setStatus(1);
            staff.setWelfareMoney(BigDecimal.valueOf(0.0));
            staffService.update(id, staff);
            return ResponseEntity.ok(new MessageResponse("successfully!"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("erorr!"));
        }
    }

    @PutMapping("/accept-all-register")
    public ResponseEntity<?> mutilpartAccept(@RequestParam("ids") List<Long> ids) {
        registerStaffService.mutipartAccept(ids);
        String.join(",", ids.stream()
                .map(value -> Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("update success"));
    }

    @PutMapping("/cancel-all-register")
    public ResponseEntity<?> mutilpartCancel(@RequestParam("ids") List<Long> ids) {
        registerStaffService.mutipartCancel(ids);
        String.join(",", ids.stream()
                .map(value -> Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("update success"));
    }
}
