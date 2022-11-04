package com.example.vmg.respository;

import com.example.vmg.model.RegisterWelfare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisterWelfareRepository extends JpaRepository<RegisterWelfare, Long> {
    @Query("select x from RegisterWelfare x where x.status = 0")
    public List<RegisterWelfare> getAllByStatus();

}
