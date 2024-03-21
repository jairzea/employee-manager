package com.company.employeemanager.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.company.employeemanager.model.User;

public class ServicesUtils {
    
    private ServicesUtils(){

    }

    public static ResponseEntity<?> gResponseEntity(String message, HttpStatus httpStatus){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("message", message);
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    public static ResponseEntity<?> message(String message, HttpStatus httpStatus){
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        return new ResponseEntity<>(responseBody, httpStatus);
    }

    public static ResponseEntity<?> validation(BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", errors);

        return ResponseEntity.badRequest().body(response);
    }

    public static ResponseEntity<?> validationFieldLogin(User user){

        if (user.getUsername() == null || user.getName() == null) {
            Map<String, Object> response = new HashMap<>();
            Map<String, String> errors = new HashMap<>();
            if (user.getUsername() == null) {
                errors.put("username", "El nombre de usuario es obligatorio");
            }
            if (user.getPassword() == null) {
                errors.put("password", "La contraseña es obligatoria");
            }

            response.put("code", HttpStatus.BAD_REQUEST.value());
            response.put("message", errors);

            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(response);
            }

        }

        return null;
    }

    public static ResponseEntity<?> buildTokenResponse(String token) {
        String message = "Usuario autenticado con éxito";
        String tokenType = "Bearer";

        return ResponseEntity.ok()
                .body(Map.of("access_token", token, "type", tokenType, "message", message));
    }
}
