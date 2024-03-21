package com.company.employeemanager.service.implementation;

import com.company.employeemanager.constants.MiscellaneousConstants;
import com.company.employeemanager.model.User;
import com.company.employeemanager.repository.UserRepository;
import com.company.employeemanager.security.CustomerDetailService;
import com.company.employeemanager.security.jwt.JwtUtil;
import com.company.employeemanager.service.AuthService;
import com.company.employeemanager.utils.ServicesUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private CustomerDetailService customerDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> signUp(User user) {
        try {

            User existingUser = userRepository.findByUsername(user.getUsername());

            if (Objects.isNull(existingUser)) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                return ServicesUtils.message("Usuario registrado exitosamente", HttpStatus.CREATED);

            }else {
                return ServicesUtils.gResponseEntity("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServicesUtils.gResponseEntity(MiscellaneousConstants.SOMETHING_WENTS_WRONGS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> signIn(User user) {

        try {

            User existingUser = userRepository.findByUsername(user.getUsername());

            if (Objects.isNull(existingUser)) {
                return ServicesUtils.gResponseEntity("El usuario no existe", HttpStatus.BAD_REQUEST);
            }
            String username = user.getUsername();
            String password = user.getPassword();
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            if(authenticate.isAuthenticated()){
                if (customerDetailService.getUserDetail().getState().equals(true)) {
                    return ServicesUtils.buildTokenResponse(jwtUtil.generateToken(customerDetailService.getUserDetail().getUsername(), customerDetailService.getUserDetail().getRol()));
                }else{
                    return ServicesUtils.gResponseEntity("Usuario inactivo", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } catch (BadCredentialsException e) {
            return ServicesUtils.gResponseEntity("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            String errorMessage = "Error durante la autenticación: " + e.getMessage();
            e.printStackTrace();
            return ServicesUtils.gResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ServicesUtils.gResponseEntity(MiscellaneousConstants.SOMETHING_WENTS_WRONGS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ArrayList<User> getUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }
    
}
