package com.example.blog.repository;

import com.example.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findByNickname(String nickname);
    User findUserByEmail(String email);
    User findUserByActivationCode(String activationCode);
    User findUserByResetCode(String resetCode);
}
