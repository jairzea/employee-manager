package com.company.employeemanager.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.company.employeemanager.security.CustomerDetailService;
import com.company.employeemanager.utils.ServicesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException  {
        if (request.getServletPath().matches(".*/auth/signin|.*v1/auth/signup")) {
            filterChain.doFilter(request, response);
        }
        else{
            
            String authorizationHeader = request.getHeader(headerName);

            if (authorizationHeader == null || !authorizationHeader.startsWith(tokenPrefix)){
                // No se proporcionó un token en la solicitud
                ResponseEntity<?> unauthorizedResponse = ServicesUtils.gResponseEntity("Se requiere autenticación. Por favor, incluya un token JWT en el encabezado Authorization.", HttpStatus.UNAUTHORIZED);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(unauthorizedResponse.getBody()));
                return;

            }
            String token = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
                token = authorizationHeader.substring(tokenPrefix.length());

                try {
                    userName =  jwtUtil.extractUserName(token);
                    claims = jwtUtil.extractAllClaims(token);
                } catch (ExpiredJwtException ex) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
                    return;
                }
                
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
