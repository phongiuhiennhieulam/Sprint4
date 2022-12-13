package com.example.vmg.service;

import com.example.vmg.model.Notification;
import com.example.vmg.respository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    public List<Notification> getNewNTF(Long idStaff){
        return notificationRepository.getNewNTF(idStaff);
    }

    public List<Notification> getOldNTF(Long idStaff){
        return notificationRepository.getOldNTF(idStaff);
    }
}
