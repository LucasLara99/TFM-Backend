package com.example.demo.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String facultad;
    private String name;
}