package com.example.vmg.controller;

import com.example.vmg.dto.respose.MessageResponse;
import com.example.vmg.model.MoneyUpdate;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.respository.MoneyUpdateRepository;
import com.example.vmg.service.MoneyUpdateService;
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
public class MoneyUpdateController {
    @Autowired
    private MoneyUpdateService moneyUpdateService;
    @Autowired
    private MoneyUpdateRepository moneyUpdateRepository;

    @Autowired
    private StaffService staffService;

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/getPage/money")
    public ResponseEntity<Page<MoneyUpdateInterface>> getPage(@RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "9") int pageSize, @RequestParam(defaultValue = "") String keyWord) {
        if (!(keyWord.equals("undefined") || keyWord == null)) {
            keyWord = keyWord.trim();
            return new ResponseEntity<Page<MoneyUpdateInterface>>(moneyUpdateService.getPage(page, pageSize, keyWord), HttpStatus.OK);
        } else {
            return null;
        }
    }

//    @GetMapping("/check/{id}")
//    public ResponseEntity<?> Check(@PathVariable Long id) {
//        return new ResponseEntity<Long>(moneyUpdateRepository.validate(id), HttpStatus.OK);
//    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/getList/moneyAccept")
    public ResponseEntity<List<MoneyUpdateInterface>> getMoneyAccept() {
        return new ResponseEntity<List<MoneyUpdateInterface>>(moneyUpdateService.getMoneyAccept(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/getList/moneyCancel")
    public ResponseEntity<List<MoneyUpdateInterface>> getMoneyCancel() {
        return new ResponseEntity<List<MoneyUpdateInterface>>(moneyUpdateService.getMoneyCancel(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @GetMapping("/getList/moneyWaiting")
    public ResponseEntity<List<MoneyUpdateInterface>> getMoneyWaiting() {
        return new ResponseEntity<List<MoneyUpdateInterface>>(moneyUpdateService.getMoneyWating(), HttpStatus.OK);
    }

    //X??t duy???t ti???n c???a l??nh ?????o
    @PutMapping("/accpet-money/{id}")
    public ResponseEntity<?> AcceptMoney(@PathVariable Long id) {
//        Long count = moneyUpdateRepository.validate(id);
//        if (count > 0) {
//            throw new RuntimeException("sfjghdjfghjkdfh");
//        }else {

        MoneyUpdate moneyUpdate = moneyUpdateService.findById(id).get();
        moneyUpdate.setStatus(1);
        moneyUpdateService.update(id, moneyUpdate);
        return ResponseEntity.ok(new MessageResponse("successfully!"));
    }

    @PutMapping("/cancel-money/{id}")
    public ResponseEntity<?> CancelMoney(@PathVariable Long id) {
        MoneyUpdate moneyUpdate = moneyUpdateService.findById(id).get();
        moneyUpdate.setStatus(2);
        moneyUpdateService.update(id, moneyUpdate);
        return ResponseEntity.ok(new MessageResponse("successfully!"));
    }

    @PutMapping("/return-money/{id}")
    public ResponseEntity<?> ReturnMoney(@PathVariable Long id) {
        MoneyUpdate moneyUpdate = moneyUpdateService.findById(id).get();
        moneyUpdate.setStatus(0);
        moneyUpdateService.update(id, moneyUpdate);
        
        return ResponseEntity.ok(new MessageResponse("successfully!"));
    }


    @PutMapping("/accept-all")
    public ResponseEntity<?> mutilpartAccept(@RequestParam("ids") List<Long> ids) {
        moneyUpdateService.mutipartAccept(ids);
        String.join(",", ids.stream()
                .map(value -> Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("update success"));
    }

    @PutMapping("/cancel-all")
    public ResponseEntity<?> mutilpartCancel(@RequestParam("ids") List<Long> ids) {
        moneyUpdateService.mutipartCancel(ids);
        String.join(",", ids.stream()
                .map(value -> Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("update success"));
    }

    @PutMapping("/return-all")
    public ResponseEntity<?> mutilpartReturn(@RequestParam("ids") List<Long> ids) {
        moneyUpdateService.mutipartReturn(ids);
        String.join(",", ids.stream()
                .map(value -> Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("update success"));
    }

//    @GetMapping("/notification-accept/{id}")
//    public ResponseEntity<String> notificationAccept(@RequestParam Long id, @RequestParam String status, @RequestParam BigDecimal welfareMoney, @RequestParam Long idStaff) {
//        return new ResponseEntity<String>(moneyUpdateService.getNotification(id,status,welfareMoney), HttpStatus.OK);
//    }



}
