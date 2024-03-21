package com.company.employeemanager.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
    
    private SecretKey secret = Keys.hmacShaKeyFor("9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9@2023$.".getBytes(StandardCharsets.UTF_8));

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, java.util.function.Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload();
    }

    private Boolean  isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String  generateToken(String userName, String role)
    { 
        Map<String, Object> claims = new HashMap<>();   
        claims.put("rol", role);
        return createToken(claims, userName);
    }     

    private String createToken(Map<String,Object> claims, String subject)  
    {                
        return Jwts.builder()      
                .claim("claims", claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100 * 60 * 60 * 10))
                .signWith(secret).compact();
    
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
