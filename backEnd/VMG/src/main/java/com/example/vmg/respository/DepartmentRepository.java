package com.example.vmg.respository;

import com.example.vmg.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("select s from Department s where s.name like  (:keyWord) ")
    Page<Department> getPage(Pageable pageable, String keyWord);

    Department getDepartmentByName(String name);
}
