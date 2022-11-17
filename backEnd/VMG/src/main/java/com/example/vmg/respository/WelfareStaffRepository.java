package com.example.vmg.respository;

import com.example.vmg.model.WelfareStaff;
import com.example.vmg.model.WelfareStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WelfareStaffRepository extends JpaRepository<WelfareStaff, Long> {
    @Query("select x from WelfareStaff x where x.status = 0 AND x.staff.id =:id ")
    List<WelfareStaff> findByStaffId(Long id);

    @Query("select x from WelfareStaff x where (x.status = 0 OR x.status = 1 OR x.status = 2) AND x.staff.id =:id ")
    List<WelfareStaff> findByAllStaffId(Long id);

    @Query("select x from WelfareStaff x where x.status = 2 ")
    public List<WelfareStaff> getByRegister();
    @Query("select x from WelfareStaffEntity x where x.status = 2 AND x.idStaff =:id ")
    List<WelfareStaffEntity> findByStaffRegisterId(Long id);
}
