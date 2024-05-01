package com.example.demo.requests;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String password;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}