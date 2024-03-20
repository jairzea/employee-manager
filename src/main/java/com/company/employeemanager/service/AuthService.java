package com.company.employeemanager.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.company.employeemanager.model.User;

public interface AuthService {
    
    ResponseEntity<String> signUp(User user);
    ArrayList<User> getUsers();
}
