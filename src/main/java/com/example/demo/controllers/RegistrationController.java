package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        // Validar los datos del usuario
        if (userRepository.findByEmail(registrationRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El email ya está registrado"));
        }

        // Hashear la contraseña
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        // Crear un nuevo objeto User con los datos del usuario y la contraseña hasheada
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(hashedPassword);
        user.setFacultad(registrationRequest.getFacultad()); // Agregar la facultad al usuario
        user.setRol("USER"); // Asignar el rol USER al usuario

        // Guardar el objeto User en la base de datos
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Registro exitoso"));
    }
}