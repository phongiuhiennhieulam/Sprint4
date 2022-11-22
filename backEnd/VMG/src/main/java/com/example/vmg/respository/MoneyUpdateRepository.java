package com.example.vmg.respository;

import com.example.vmg.model.MoneyUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyUpdateRepository extends JpaRepository<MoneyUpdate, Long> {
}
