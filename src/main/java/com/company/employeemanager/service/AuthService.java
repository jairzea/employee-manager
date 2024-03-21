package com.company.employeemanager.service;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;

import com.company.employeemanager.model.User;

public interface AuthService {
    
    ResponseEntity<?> signUp(User user);
    ResponseEntity<?> signIn(User user);
    ArrayList<User> getUsers();
}
