package com.example.vmg.respository;

import com.example.vmg.model.GeneralWelfare;
import com.example.vmg.model.Welfare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneralWelfareRepository extends JpaRepository<GeneralWelfare, Long> {
    @Query("select sum(x.price) from GeneralWelfare x")
    Long getAllMoneyGeneral();
    List<GeneralWelfare> findAllByOrderByIdDesc();

}
