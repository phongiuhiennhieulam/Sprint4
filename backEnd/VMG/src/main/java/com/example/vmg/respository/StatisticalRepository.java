package com.example.vmg.respository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StatisticalRepository {
    @Modifying
    @Transactional
    @Query("update Staff p set p.status = 0 where p.id =:id")
    void unlock(Long id);
}
