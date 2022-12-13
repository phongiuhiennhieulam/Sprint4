package com.example.vmg.respository;

import com.example.vmg.model.MoneyUpdate;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.model.OderMoneyInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("update MoneyUpdate p set p.status = 1 where p.id in(:ids)")
    void MutipartAccept(List<Long> ids);

    @Modifying
    @Transactional
    @Query("update MoneyUpdate p set p.status = 2 where p.id in(:ids)")
    void MutipartCancel(List<Long> ids);

    @Modifying
    @Transactional
    @Query("update MoneyUpdate p set p.status = 0 where p.id in(:ids)")
    void MutipartReturn(List<Long> ids);
    @Query("select mu.id as id,\n" +
            "                               s.name as name,\n" +
            "                               s.welfareMoney as moneyOld,\n" +
            "                               s.code as code,\n" +
            "                               d.name as department,\n" +
            "                               mu.moneyUpdate as moneyNew,\n" +
            "                               u.name as nameAdmin,\n" +
            "                               mu.status as status\n" +
            "                        from Staff  s, MoneyUpdate mu, User u, Department d\n" +
            "                        where s.code = mu.maNV and mu.idStaff = u.id and d.id = s.department.id and s.status = 0 and mu.maNV  like (:keyWord) order by mu.id desc")
    Page<MoneyUpdateInterface> getPage(Pageable pageable, String keyWord);

    @Query("select mu.id as id,\n" +
            "                               s.name as name,\n" +
            "                               s.welfareMoney as moneyOld,\n" +
            "                               s.code as code,\n" +
            "                               d.name as department,\n" +
            "                              mu.moneyUpdate as moneyNew,\n" +
            "                               u.name as nameAdmin,\n" +
            "                               mu.status as status\n" +
            "                        from Staff  s, MoneyUpdate mu, User u, Department d\n" +
            "                        where s.code = mu.maNV and mu.idStaff = u.id and d.id = s.department.id and mu.status = 1")
    List<MoneyUpdateInterface> getMonneyAccept();

    @Query("select mu.id as id,\n" +
            "                               s.name as name,\n" +
            "                               s.welfareMoney as moneyOld,\n" +
            "                               s.code as code,\n" +
            "                               d.name as department,\n" +
            "                              mu.moneyUpdate as moneyNew,\n" +
            "                               u.name as nameAdmin,\n" +
            "                               mu.status as status\n" +
            "                        from Staff  s, MoneyUpdate mu, User u, Department d\n" +
            "                        where s.code = mu.maNV and mu.idStaff = u.id and d.id = s.department.id and mu.status = 2")
    List<MoneyUpdateInterface> getMonneyCancel();

    @Query("select mu.id as id,\n" +
            "                               s.name as name,\n" +
            "                               s.welfareMoney as moneyOld,\n" +
            "                               s.code as code,\n" +
            "                               d.name as department,\n" +
            "                              mu.moneyUpdate as moneyNew,\n" +
            "                               u.name as nameAdmin,\n" +
            "                               mu.status as status\n" +
            "                        from Staff  s, MoneyUpdate mu, User u, Department d\n" +
            "                        where s.code = mu.maNV and mu.idStaff = u.id and d.id = s.department.id and mu.status = 0")
    List<MoneyUpdateInterface> getMonneyWaiting();

//    String getNotification();

//    @Query("select count(mu) from MoneyUpdate mu where mu.status = 1 and mu.idStaff = :idStaff ")
//    Long validate(Long idStaff);

}
