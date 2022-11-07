package com.example.vmg.respository;

import com.example.vmg.model.Staff;
import com.example.vmg.model.StaffInterface;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    @Query("update Staff p set p.status = 1 where p.id =:id")
    void delete(Long id);

    @Modifying
    @Transactional
    @Query("update Staff p set p.status = 0 where p.id =:id")
    void unlock(@Param("id") Long id);

    Staff findByEmail(String email);

    List<Staff> findByDepartment_Id(Long id);

    @Modifying
    @Transactional
    @Query("select sum(w.price) from WelfareStaff ws join Welfare w on ws.welfare.id = w.id where ws.welfare.id = :id GROUP BY ws.staff.id")
    Integer getMoney(@Param("id") Long id);

    @Query("select s from Staff s")
    public Page<Staff> getPage(Pageable pageable);

    @Query("select s from Staff s where month(s.date) = :number ")
    public List<Staff> getSinhNhat(int number);

    @Query("select code from Staff")
    public List<String> getCode();

    @Query("select email from Staff")
    public List<String> getEmail();


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



}
