package com.example.vmg.respository;


import com.example.vmg.model.GeneralWelfare;
import com.example.vmg.model.Welfare;
import com.example.vmg.model.WelfareSatistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


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
            "            where ws.status = 0 and w.status = 0 and w.id = ws.id_welfare\n" +
            "            group by ws.id_welfare\n" +
            "            ORDER BY quantity desc",nativeQuery = true)
    List<WelfareSatistic> getAllStatistic();

    List<Welfare> findAllByOrderByIdDesc();

    @Query("select x from Welfare x where x.status = 0")
    List<Welfare> getListWelfareByStatus();



}
