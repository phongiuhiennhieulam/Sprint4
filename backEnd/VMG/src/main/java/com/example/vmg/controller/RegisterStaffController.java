package com.example.vmg.controller;

import com.example.vmg.dto.respose.MessageResponse;
import com.example.vmg.model.*;
import com.example.vmg.respository.NotificationRepository;
import com.example.vmg.respository.StaffRepository;
import com.example.vmg.service.MoneyUpdateService;
import com.example.vmg.service.RegisterStaffService;
import com.example.vmg.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RegisterStaffController {
    @Autowired private StaffService staffService;
    @Autowired private RegisterStaffService registerStaffService;
    @Autowired private MoneyUpdateService moneyUpdateService;
    @Autowired private NotificationRepository notificationRepository;


    //    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/list-register")
    public ResponseEntity<Page<NewStaffInterface>> getListRegister(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "9") int pageSize, @RequestParam(defaultValue = "") String keyWord){
        if (!(keyWord.equals("undefined") || keyWord == null)) {
            keyWord = keyWord.trim();
            return new ResponseEntity<Page<NewStaffInterface>>(registerStaffService.getRegister(page, pageSize,keyWord), HttpStatus.OK);
        } else {
            return null;
        }
    }

    @PutMapping("/accept-register/{id}/{money}/{moneyId}")
    public ResponseEntity <?> AcceptRegisters(@PathVariable Long id,@PathVariable BigDecimal money,@PathVariable Long moneyId){
        try {
            Staff staff = staffService.getById(id);
            staff.setStatus(0);
            staff.setWelfareMoney(money);
            moneyUpdateService.delete(moneyId);
            staffService.update(id, staff);

            //gui thong bao
            Notification notification = new Notification();

            notification.setIdStaff(staff.getId());
            notification.setMessage("Bạn đã đc xét duyệt số tiền hỗ trợ phúc lợi"  );
            Date date = new Date();
            notification.setDate(date);
            notification.setStatus(0);
            notification.setCategory(0);
            notificationRepository.save(notification);
            return ResponseEntity.ok(new MessageResponse("successfully!"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("erorr!"));
        }
    }

    @PutMapping("/cancel-register/{id}/{money}")
    public ResponseEntity <?> CancelRegisters(@PathVariable Long id,@PathVariable Long money){
        try {
            Staff staff = staffService.getById(id);
            staff.setStatus(3);
            staff.setWelfareMoney(BigDecimal.valueOf(0.0));
            moneyUpdateService.delete(money);
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
