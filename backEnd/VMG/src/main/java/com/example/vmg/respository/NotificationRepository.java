package com.example.vmg.respository;

import com.example.vmg.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Transactional
    @Query("select s from Notification s where s.status = 0 and s.idStaff = :idStaff")
    List<Notification> getNewNTF(Long idStaff);

    @Modifying
    @Transactional
    @Query("select s from Notification s where s.status = 1 and s.idStaff = :idStaff")
    List<Notification> getOldNTF(Long idStaff);


}
