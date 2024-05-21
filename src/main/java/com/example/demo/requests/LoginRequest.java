package com.example.demo.requests;

import lombok.Data;

@Data
public class LoginRequest {
    public String email;
    public String password;
}
