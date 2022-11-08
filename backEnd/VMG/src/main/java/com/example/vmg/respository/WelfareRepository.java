package com.example.vmg.respository;

import com.example.vmg.model.Welfare;
import com.example.vmg.model.WelfareSatistic;
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

    @Query(value = "select w.name,count(ws.id_welfare) as quantity from welfare_staff ws, welfare w\n" +
            "            where ws.id_welfare = ws.id_welfare and ws.status = 0 and w.id = ws.id_welfare\n" +
            "            group by ws.id_welfare\n" +
            "            ORDER BY quantity desc",nativeQuery = true)
    List<WelfareSatistic> getAllStatistic();

}
