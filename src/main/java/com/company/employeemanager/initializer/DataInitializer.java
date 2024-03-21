package com.company.employeemanager.initializer;

import com.company.employeemanager.model.User;
import com.company.employeemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Consultar el usuario admin
        User admin = userRepository.findByUsername("admin");
        
        if (admin != null) {
            // Actualizar la contraseña del usuario admin
            String encodedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encodedPassword);
            userRepository.save(admin);
            System.out.println("Contraseña del usuario admin actualizada correctamente.");
        } else {
            System.out.println("El usuario admin no existe en la base de datos.");
        }
    }
}
