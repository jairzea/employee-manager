package com.company.employeemanager.security.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
                            
                            String formattedDate = dateFormat.format(new Date());

                            Map<String, Object> body = new HashMap<>();
                            body.put("timestamp", formattedDate);
                            body.put("mensaje", "Acceso denegado!");
                            body.put("error", authException.getMessage());
                            body.put("path", request.getRequestURI());

                            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                            response.setStatus(403);
                            response.setContentType("application/json");


                        }
}
