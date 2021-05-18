package com.example.blog.service;

import com.example.blog.model.RegistrationRequest;
import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    public BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;
//    @Autowired
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    public User addUser(User user) throws Exception {
//        if(userRepository.findByNickname(user.getNickname()) == null && userRepository.findUserByEmail(user.getEmail()) == null) {
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            user.setRole(Role.USER);
//            String activationCode = UUID.randomUUID().toString();
//            user.setActivationCode(activationCode);
//            emailService.sendMessage(user.getEmail(), "Activation", "Follow the link to activate your profile\n" +
//                    "http://localhost:4200/activation/" + activationCode);
//            return userRepository.save(user);
//        } else {
//            throw new Exception("User with the same nickname or email already exists");
//        }
//    }

    public User addUser(RegistrationRequest request) throws Exception {
        System.out.println(request.getPath());
        if(userRepository.findByNickname(request.getNickname()) == null && userRepository.findUserByEmail(request.getEmail()) == null) {
            User user = new User();
            user.setNickname(request.getNickname());
            user.setEmail(request.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);
            String activationCode = UUID.randomUUID().toString();
            user.setActivationCode(activationCode);
            emailService.sendMessage(user.getEmail(), "Activation", "Follow the link to activate your profile\n" +
                    request.getPath() + "/activation/" + activationCode);
            return userRepository.save(user);
        } else {
            throw new Exception("User with the same nickname or email already exists");
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByNickname(String nickname){
        return userRepository.findByNickname(nickname);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void activateUser(String activationCode) {
        User user = userRepository.findUserByActivationCode(activationCode);
        user.setActivationCode(null);
        userRepository.save(user);
    }

    public User promoteToAdmin(Long id) {
        User user = findUserById(id);
        user.setRole(Role.ADMIN);
        updateUser(user);
        return user;
    }

    public void sendResetEmail(String email, String path) {
        User user = userRepository.findUserByEmail(email);
        if (user != null){
            user.setResetCode(UUID.randomUUID().toString());
            userRepository.save(user);
            emailService.sendMessage(email, "Password reset", "Follow the link to reset your password\n" +
                    path + "/reset/code/" + user.getResetCode());
        }
    }

    public void resetPassword(String resetCode, String newPassword){
        User user = userRepository.findUserByResetCode(resetCode);
        if(user != null){
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            user.setResetCode(null);
            userRepository.save(user);
        }
    }
}
