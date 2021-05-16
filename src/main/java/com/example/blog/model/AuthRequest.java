package com.example.blog.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String nickname;
    private String password;

    public AuthRequest() { }

    public AuthRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
