package com.company.employeemanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.employeemanager.constants.MiscellaneousConstants;
import com.company.employeemanager.model.User;
import com.company.employeemanager.service.AuthService;
import com.company.employeemanager.utils.ServicesUtils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> userRegister(@Valid @RequestBody User user, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return ServicesUtils.validation(bindingResult);
        }
        try {
            ResponseEntity<?> response = authService.signUp(user);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServicesUtils.gResponseEntity(MiscellaneousConstants.SOMETHING_WENTS_WRONGS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody User user){

        if (!Objects.isNull(ServicesUtils.validationFieldLogin(user))) {
            return  ServicesUtils.validationFieldLogin(user);
        }
        
        try {
            ResponseEntity<?> response = authService.signIn(user);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServicesUtils.gResponseEntity(MiscellaneousConstants.SOMETHING_WENTS_WRONGS, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/users")
    public ArrayList<User> getCoins() {
        return authService.getUsers();
    }

}
