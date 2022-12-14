package com.example.vmg.controller;


import com.example.vmg.model.MoneyUpdate;
import com.example.vmg.model.Staff;
import com.example.vmg.respository.MoneyUpdateRepository;
import com.example.vmg.service.MoneyUpdateService;
import com.example.vmg.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AutoController {

    @Autowired
    private MoneyUpdateService moneyUpdateService;
    @Autowired
    private MoneyUpdateRepository moneyUpdateRepository;

    @Autowired private StaffService staffService;
//    private static final Logger log = LoggerFactory.getLogger(ScheduleTaskFixedRate.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


//    @Scheduled(fixedRate =30000)
//    public void reportCurrentTime() {
//        List<MoneyUpdate> moneyUpdates = moneyUpdateService.findAll();
//
//        for (int i = 0; i<moneyUpdates.size();i++){
//            if(moneyUpdates.get(i).getStatus() == `1`)
//            {
//                Staff staff = staffService.findByCode(moneyUpdates.get(i).getMaNV()).get(0);
//                staff.setWelfareMoney(moneyUpdates.get(i).getMoneyUpdate());
//                staffService.saveOrUpDate(staff);
//                moneyUpdateRepository.deleteAll();
//            }
//        }
//
//    }


}
