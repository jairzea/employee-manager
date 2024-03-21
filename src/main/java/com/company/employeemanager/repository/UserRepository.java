package com.company.employeemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.employeemanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
