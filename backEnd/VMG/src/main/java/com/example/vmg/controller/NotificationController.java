package com.example.vmg.controller;

import com.example.vmg.model.Notification;
import com.example.vmg.model.Staff;
import com.example.vmg.model.User;
import com.example.vmg.respository.NotificationRepository;
import com.example.vmg.service.NotificationService;
import com.example.vmg.service.StaffService;
import com.example.vmg.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private StaffService  staffService;
    @Autowired private NotificationRepository notificationRepository;

    @GetMapping("/nft-new")
    public List<Notification> getNew(@RequestParam("email") String email){
        User user = userService.findByUsername(email).get();
        Staff staff = staffService.getByEmail(user.getUserName());
        List<Notification> notifications = notificationService.getNewNTF(staff.getId());
        return notifications;
    }

    @GetMapping("/nft-old")
    public List<Notification> getold(@RequestParam("email") String email){
        User user = userService.findByUsername(email).get();
        System.out.println(user.getUserName());
        Staff staff = staffService.getByEmail(user.getUserName());
        System.out.println(staff.getId());
        List<Notification> notifications = notificationService.getOldNTF(staff.getId());
        return notifications;
    }

    @PutMapping("/seen")
    public String Senn(@RequestParam("email") String email){
        User user = userService.findByUsername(email).get();
        Staff staff = staffService.getByEmail(user.getUserName());
        List<Notification> notifications = notificationService.getNewNTF(staff.getId());
        for (Notification n : notifications){
            Notification notification = notificationRepository.findById(n.getId()).get();
            notification.setStatus(1);
            notificationRepository.save(notification);
        }
     return "Seen";
    }
}
