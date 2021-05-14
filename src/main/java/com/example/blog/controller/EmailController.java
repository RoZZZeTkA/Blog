package com.example.blog.controller;

import com.example.blog.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String sendEmail(){
        emailService.sendMessage("sgatbf@gmail.com", "subject", "test text");
        return "send";
    }
}
