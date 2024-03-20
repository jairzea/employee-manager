package com.company.employeemanager.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.employeemanager.constants.MiscellaneousConstants;
import com.company.employeemanager.model.User;
import com.company.employeemanager.service.AuthService;
import com.company.employeemanager.utils.ServicesUtils;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> userRegister(@RequestBody(required = true)User user){
        try {
            authService.signUp(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServicesUtils.gResponseEntity(MiscellaneousConstants.SOMETHING_WENTS_WRONGS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping()
    public ArrayList<User> getCoins() {
        return authService.getUsers();
    }
}
