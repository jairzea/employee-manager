package com.company.employeemanager.security;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.employeemanager.model.User;
import com.company.employeemanager.repository.UserRepository;

@Service
public class CustomerDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private User userDetail;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userDetail = userRepository.findByUsername(username);
        
        if (!Objects.isNull(userDetail)){

            return new org.springframework.security.core.userdetails.User(userDetail.getUsername(), userDetail.getPassword(), new ArrayList<>());
        
        }else{

            throw new UsernameNotFoundException("Usuario no encontrado");

        }
        
    }

    public User getUserDetail() { 
        return userDetail;
    }
}
