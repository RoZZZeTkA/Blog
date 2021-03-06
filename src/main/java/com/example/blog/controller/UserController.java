package com.example.blog.controller;

import com.example.blog.model.RegistrationRequest;
import com.example.blog.model.User;
import com.example.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        return new ResponseEntity<>(userService.findUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName()), HttpStatus.OK);
    }

    @GetMapping("/activation/{activationCode}")
    public void activateUser(@PathVariable("activationCode") String activationCode) {
        userService.activateUser(activationCode);
    }

    @PostMapping("/reset/email")
    public ResponseEntity<?> sendResetEmail(@RequestParam("email") String email, @RequestParam("path") String path) {
        userService.sendResetEmail(email, path);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(@RequestParam("resetCode") String resetCode, @RequestParam("newPassword") String newPassword) {
        userService.resetPassword(resetCode, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/promote/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> promoteToAdmin(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.promoteToAdmin(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody RegistrationRequest user) {
        try {
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
