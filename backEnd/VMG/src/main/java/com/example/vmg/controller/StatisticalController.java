package com.example.vmg.controller;

import com.example.vmg.model.CostInterface;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.model.Staff;
import com.example.vmg.model.StatisticalInterface;
import com.example.vmg.respository.MoneyUpdateRepository;
import com.example.vmg.respository.StatisticalRepository;
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

    @GetMapping("/getList/money")
    public ResponseEntity<List<MoneyUpdateInterface>> getList(){
        return new ResponseEntity<List<MoneyUpdateInterface>>(moneyUpdateRepository.getList(), HttpStatus.OK);
    }

}
