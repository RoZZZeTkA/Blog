package com.example.blog.service;

import com.example.blog.model.Mark;
import com.example.blog.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MarkService {

    @Autowired
    public MarkRepository markRepository;

    @Autowired
    public UserService userService;

    public Mark addMark(Long postId, int value) {
        return markRepository.save(new Mark(postId, userService.findUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName()).getId(), value));
    }
}
