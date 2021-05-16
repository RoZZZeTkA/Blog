package com.example.blog.model;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String nickname;
    private String email;
    private String password;
    private String activationCode;
    private String path;
}
