package com.example.vmg.respository;

import com.example.vmg.model.Welfare;
import com.example.vmg.model.WelfareStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface WelfareRepository extends JpaRepository<Welfare, Long> {
//    @Modifying
//    @Transactional
//    @Query()
//    void saveRegisterWelfare(Long id_welfare,Long id_user);
    @Query(value = "select s.welfare_money from Staff s where s.id = ?",nativeQuery = true)
    BigDecimal getAllMoney(Long id);

}
