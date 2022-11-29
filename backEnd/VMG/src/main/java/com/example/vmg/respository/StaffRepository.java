package com.example.vmg.respository;

import com.example.vmg.model.CostInterface;
import com.example.vmg.model.Staff;
import com.example.vmg.model.StaffInterface;
import com.example.vmg.model.StatisticalInterface;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {

    @Modifying
    @Transactional
    @Query("update Staff p set p.welfareMoney = :number where p.id in(:longs)")
    void updateMoney(BigDecimal number, List<Long> longs);


    @Modifying
    @Transactional
    @Query("update Staff p set p.status = 1 where p.id in(:longs)")
    void MutipartDelete(List<Long> longs);

    @Modifying
    @Transactional
    @Query("update WelfareStaff p set p.status = 2 where p.id in(:longs)")
    public void MutipartReturn(List<Long> longs);

    @Query("select s.email from Staff s where s.id in(:longs)")
    public List<String> getEmailById(List<Long> longs);
    @Query("select email from Staff")
    public List<String> getEmail();
    @Modifying
    @Transactional
    @Query("update Staff p set p.status = 1 where p.id =:id")
    void delete(Long id);

    @Modifying
    @Transactional
    @Query("update Staff p set p.status = 0 where p.id =:id")
    void unlock(Long id);

    Staff findByEmail(String email);

    List<Staff> findByDepartment_Id(Long id);

    @Modifying
    @Transactional
    @Query("select sum(w.price) from WelfareStaff ws join Welfare w on ws.welfare.id = w.id and w.status = 0 " +
            "where ws.welfare.id = :id GROUP BY ws.staff.id")
    Long getMoney(@Param("id") Long id);

    @Query("select s from Staff s order by s.id desc")
    public Page<Staff> getPage(Pageable pageable);

    @Query("select s from Staff s where month(s.date) = :number ")
    public List<Staff> getSinhNhat(int number);

    @Query("select code from Staff")
    public List<String> getCode();

    @Query(value = "select *  from staff s\n" +
            "left join (select ws.id_staff as id_staff,  sum(ws.quantity * w.price)  as total from welfare_staff ws\n" +
            "left join welfare w on ws.id_welfare = w.id and ws.status = 0\n" +
            "group by ws.id_staff) as n\n" +
            "on s.id = n.id_staff\n" +
            "having (IFNULL(n.total,0) + (select sum(price) from general_welfare)) > s.welfare_money",nativeQuery = true)
    public List<StaffInterface> getErorr();

    @Query("SELECT DISTINCT b FROM Staff b JOIN b.department c " +
            "WHERE b.status = 0 and b.name LIKE %?1% " +
            "OR c.name LIKE %?1%" +
            "OR b.code LIKE %?1%"
    )
    public Page<Staff> finByText(String text, Pageable pageable);

    @Query(value = "select s from Staff s where s.email like :email")
    public Staff finByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "select s.email from Staff s where s.id not LIKE :id", nativeQuery = true)
    public List<String> getEmailByUpdate(Long id);
    @Modifying
    @Transactional
    @Query(value = "select s.code from Staff s where s.id not LIKE :id", nativeQuery = true)
    public List<String> getCodeByUpdate(Long id);

    @Modifying
    @Transactional
    @Query(value = "select s.id, s.name, s.code ,count(s.id) as quantity from staff s, welfare_staff ws\n" +
            "where ws.id_welfare = ? and s.id = ws.id_staff and ws.status=0 \n " +
            "group by s.id", nativeQuery = true)
    public List<StatisticalInterface> getStaffByWelfare(Long id);

    @Modifying
    @Transactional
    @Query( value = "select d.name as name, sum(s.welfare_money) as total, count(s.id) as quantity\n" +
            "from department d, staff s\n" +
            "where d.id = s.id_department and s.status = 0\n" +
            "group by s.id_department ", nativeQuery = true)
    public List<CostInterface> getCost();

}
