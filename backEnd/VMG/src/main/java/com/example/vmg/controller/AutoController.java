package com.example.vmg.controller;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AutoController {
//    private static final Logger log = LoggerFactory.getLogger(ScheduleTaskFixedRate.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(cron = "0 45 14 * * *")
//    public void reportCurrentTime() {
//        System.out.println("hello khang");
//    }

}
