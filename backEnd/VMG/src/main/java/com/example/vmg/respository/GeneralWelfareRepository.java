package com.example.vmg.respository;

import com.example.vmg.model.GeneralWelfare;
import com.example.vmg.model.Welfare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneralWelfareRepository extends JpaRepository<GeneralWelfare, Long> {
    @Query(value = "select sum(x.price) from general_welfare x", nativeQuery = true)
    Long getAllMoneyGeneral();
    List<GeneralWelfare> findAllByOrderByIdDesc();

}
