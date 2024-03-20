package com.company.employeemanager.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServicesUtils {
    
    private ServicesUtils(){

    }

    public static ResponseEntity<String> gResponseEntity(String message, HttpStatus httpStatus){
        return new ResponseEntity<String>("Mensaje : " + message,httpStatus);
    }
}
