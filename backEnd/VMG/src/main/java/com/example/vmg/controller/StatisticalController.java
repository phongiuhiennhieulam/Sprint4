package com.example.vmg.controller;

import com.example.vmg.model.CostInterface;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.model.Staff;
import com.example.vmg.model.StatisticalInterface;
import com.example.vmg.respository.MoneyUpdateRepository;
import com.example.vmg.respository.StatisticalRepository;
import com.example.vmg.service.MoneyUpdateService;
import com.example.vmg.service.StaffService;
import org.apache.tomcat.util.json.JSONParser;
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
    @Autowired
    private MoneyUpdateRepository moneyUpdateRepository;
    @Autowired
    private MoneyUpdateService moneyUpdateService;
    @GetMapping("/staffs-by-welfare/{id}")
    public ResponseEntity<List<StatisticalInterface>> getList(@PathVariable Long id){
        return new ResponseEntity<List<StatisticalInterface>>(staffService.getStaffByWelfare(id), HttpStatus.OK);
    }
    @GetMapping("/costs")
    public ResponseEntity<List<CostInterface>> getCost(){
        return new ResponseEntity<List<CostInterface>>(staffService.getCost(), HttpStatus.OK);
    }
    @GetMapping("/getvui")
    public String getVui(){
        String ok = "{"
                    + "\"name\":\"khang\""
                    + ",\"age\":\"17\""
                    + "}";
        return ok;
    }

//    @GetMapping("/getList/money")
//    public ResponseEntity<Page<MoneyUpdateInterface>> getList(@RequestParam(defaultValue = "0") int page
//            , @RequestParam(defaultValue = "10") int pageSize){
//        try {
//            return new ResponseEntity<Page<MoneyUpdateInterface>>(moneyUpdateService.getByPage(page, pageSize), HttpStatus.OK);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }



}
