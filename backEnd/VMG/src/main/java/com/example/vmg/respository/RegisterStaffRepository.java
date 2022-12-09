package com.example.vmg.respository;

import com.example.vmg.model.NewStaffInterface;
import com.example.vmg.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RegisterStaffRepository extends JpaRepository<Staff,Long> {


    @Query(value = "select s.id,m.id as moneyId,s.code,s.date,s.email,s.name,s.status,m.money_update,s.id_department,d.name as dname from staff s,money_update m,department d\n" +
            "            where (s.status = 2 or  s.status = 3 )  and s.code = m.manv and s.id_department = d.id order by s.id desc", nativeQuery = true)
    Page<NewStaffInterface> getRegister(Pageable pageable, String keyWord);

    @Modifying
    @Transactional
    @Query("update Staff p set p.status = 0 where p.id in(:ids)")
    void MutipartAccept(List<Long> ids);

    @Modifying
    @Transactional
    @Query(value = "update Staff p set p.status = 3 , p.welfareMoney = 0.0  where p.id in(:ids)")
    void MutipartCancel(List<Long> ids);
}
