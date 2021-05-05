package com.example.blog.repository;

import com.example.blog.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Mark findMarkByPostIdAndUserId(Long postId, Long userId);
}
