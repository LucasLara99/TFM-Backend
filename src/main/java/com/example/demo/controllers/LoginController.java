package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.requests.LoginRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = authenticationService.authenticateUser(loginRequest.email, loginRequest.password);
        if (user != null) {
            String token = jwtUtil.generateToken(user);
            jwtUtil.parseJwtToken(token); // Esto es solo para verificar el token en el servidor

            // Crea un objeto de respuesta que contiene el usuario y el token
            Map<String, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);

            return ResponseEntity.ok(response); // Devuelve el objeto de respuesta
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}