package com.example.demo.controllers;

import com.example.demo.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private Long expirationTime;

    public JwtUtil() {
        // Generate a secure key
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.expirationTime = 3600000L; // Set your desired expiration time here
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public Map<String, String> parseJwtToken(String token) {
        String[] parts = token.split("\\."); // Divide el token en partes
        Map<String, String> parsedToken = new HashMap<>();
        if (parts.length == 3) { // Verifica si el token tiene tres partes
            String header = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);

            parsedToken.put("header", header);
            parsedToken.put("payload", payload);
        }
        return parsedToken;
    }
}