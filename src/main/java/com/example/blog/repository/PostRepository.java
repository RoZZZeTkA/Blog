package com.example.blog.repository;

import com.example.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);
    Post findPostByUserIdAndTitle(Long userId, String title);
    ArrayList<Post> findPostsByUserId(Long userId);
}
