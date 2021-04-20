package com.example.blog.controller;

import com.example.blog.model.AuthRequest;
import com.example.blog.jwt.JwtUtil;
import com.example.blog.model.User;
import com.example.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/")
    public String welcome(Authentication authentication){
        return "Hello " + authentication;
    }

    @GetMapping("/userPanel")
    public String userPanel(){
        return "Hello User";
    }

    @GetMapping("/adminPanel")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminPanel(){
        return "Hello Admin";
    }

    @PostMapping("/auth")
    public String generateToken(@RequestBody AuthRequest user) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getNickname(), user.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid nickname or password");
        }
        return jwtUtil.generateToken(user.getNickname(), userService.findUserByNickname(user.getNickname()).getRole().name());
    }
}
