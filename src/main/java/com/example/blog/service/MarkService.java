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
        Long userId = userService.findUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        Mark mark = markRepository.findMarkByPostIdAndUserId(postId, userId);
        if(mark == null)
            return markRepository.save(new Mark(postId, userId, value));
        else {
            if (mark.getValue() == value){
                markRepository.deleteById(mark.getId());
                return mark;
            }
            else {
                mark.setValue(value);
                return markRepository.save(mark);
            }
        }
    }

    public void deleteMarksByPostId(Long postId){
        markRepository.deleteMarksByPostId(postId);
    }
}
