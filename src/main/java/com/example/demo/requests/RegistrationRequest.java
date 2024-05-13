package com.example.demo.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String facultad;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String email, String password, String facultad) {
        this.email = email;
        this.password = password;
        this.facultad = facultad;
    }
}