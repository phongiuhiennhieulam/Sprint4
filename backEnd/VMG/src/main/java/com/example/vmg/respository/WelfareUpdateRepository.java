package com.example.vmg.respository;

import com.example.vmg.model.WelfareUpdate;
import com.example.vmg.model.WelfareUpdateInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WelfareUpdateRepository extends JpaRepository<WelfareUpdate,Long> {

    @Query(value = "(select wd.id,s.name as nameStaff,wd.id_welfare,wd.money_update,wd.year,wd.is_general,wd.name,wd.text,wd.is_quantity,wd.id_staff,w.price\n" +
            " from welfare_update wd left join welfare w on  w.id = wd.id_welfare join staff s on s.id = wd.id_staff where wd.is_general = 0 and wd.status = 2)\n" +
            "union\n" +
            "(select wu.id,t.name as nameStaff,wu.id_welfare,wu.money_update,wu.year,wu.is_general,wu.name,wu.text,wu.is_quantity,wu.id_staff,gw.price\n" +
            " from welfare_update wu left join general_welfare gw on  gw.id = wu.id_welfare join staff t on t.id = wu.id_staff  where wu.is_general = 1 and wu.status = 2)",nativeQuery = true)
    public List<WelfareUpdateInterface> getAllWelfareUpdateApproval();

    @Transactional
    @Modifying
    @Query(value = "update welfare_update set status = ?1 where welfare_update.id = ?2",nativeQuery = true)
    public void updateStatus(int status,Long id);

    @Query(value="select distinct w.id_welfare from welfare_update w where w.id_welfare is not null and w.status = 2",nativeQuery = true)
    public List<Integer> getIdOfWelfareWaitToUpdate();


}
