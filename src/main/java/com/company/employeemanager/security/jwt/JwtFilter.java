package com.company.employeemanager.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.employeemanager.security.CustomerDetailService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    String tokenPrefix = "Bearer ";
    String headerName = "Authorization";
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerDetailService customerDetailService;

    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException  {
        if (request.getServletPath().matches("/user/login|user/forgotPassword|/auth/signup")) {
            filterChain.doFilter(request, response);
        }
        else{
            String authorizationHeader = request.getHeader(headerName);
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
                token = authorizationHeader.substring(7);
                userName =  jwtUtil.extractUserName(token);
                claims = jwtUtil.extractAllClaims(token);
                
            } 
            if(userName != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customerDetailService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                
            }
            filterChain.doFilter(request, response);
        }
    }

    public String getCurrentUser(){
        return userName;
    }
}
