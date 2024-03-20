package com.company.employeemanager.service.implementation;

import com.company.employeemanager.constants.MiscellaneousConstants;
import com.company.employeemanager.model.User;
import com.company.employeemanager.repository.UserRepository;
import com.company.employeemanager.service.AuthService;
import com.company.employeemanager.utils.ServicesUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> signUp(User user) {
        try {

            User existingUser = userRepository.findByUsername(user.getUsername());

            if (Objects.isNull(existingUser)) {
                userRepository.save(user);
                return ServicesUtils.gResponseEntity("Usuario registrado exitosamente", HttpStatus.CREATED);

            }else {
                return ServicesUtils.gResponseEntity("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServicesUtils.gResponseEntity(MiscellaneousConstants.SOMETHING_WENTS_WRONGS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ArrayList<User> getUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    private boolean validateSignUpMap(Map<String, String> requestMap){
        if(requestMap.containsKey("username")){
            return true;
        }
        return false;
    }
    
}
