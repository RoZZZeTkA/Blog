package com.example.blog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String activationCode;
    private String resetCode;

    @Enumerated(EnumType.STRING)
    private Role role;
}
