package com.example.vmg.respository;

import com.example.vmg.model.MoneyUpdate;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.model.StatisticalInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MoneyUpdateRepository extends JpaRepository<MoneyUpdate, Long> {
    @Modifying
    @Transactional
    @Query(value = "select s.name as name, s.welfare_money as moneyOld,\n" +
            "       d.name as department,\n" +
            "       mu.money_update as moneyNew,\n" +
            "       u.name as nameAdmin, mu.status\n" +
            "from staff s, money_update mu, users u, department d\n" +
            "where s.id = mu.manv and mu.id_staff = u.id and d.id = s.id_department", nativeQuery = true)
    public List<MoneyUpdateInterface> getList();
}
