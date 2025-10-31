package com.example.Group3.confict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Group3.confict.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUserNameAndPassword(String userName, String password);
}
