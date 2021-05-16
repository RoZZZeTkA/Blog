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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @GetMapping("/")
    public String welcome(Authentication authentication, HttpServletRequest request){
//        return "Hello " + authentication + "\n";
        return "protocol: " + request.getProtocol() + "\nserver name: " + request.getServerName() + "\nserver port: " + request.getServerPort();
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
        if (userService.findUserByNickname(user.getNickname()).getActivationCode() != null){
            throw new Exception("Non-activated profile");
        }
        return jwtUtil.generateToken(user.getNickname(), userService.findUserByNickname(user.getNickname()).getRole().name());
    }
}
