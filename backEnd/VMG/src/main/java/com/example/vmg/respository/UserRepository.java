package com.example.vmg.respository;

import com.example.vmg.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String userName);

    @Query("select s from User s where s.name like (:keyWord) or s.userName like (:keyWord) order by s.id DESC")
    public Page<User> getPage(Pageable pageable, String keyWord);

    @Modifying
    @Transactional
    @Query("update User p set p.status = 1 where p.userName in(:emails)")
    void looks(List<String> emails);

//    Boolean existsByEmail(String email);
}
