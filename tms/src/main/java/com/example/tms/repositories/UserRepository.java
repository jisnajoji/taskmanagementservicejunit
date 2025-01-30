package com.example.tms.repositories;

import com.example.tms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM `user` WHERE username = ?1", nativeQuery = true)
    User findByUsername(String username);
}
