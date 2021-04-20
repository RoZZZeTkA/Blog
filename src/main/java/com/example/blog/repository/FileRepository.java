package com.example.blog.repository;

import com.example.blog.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface FileRepository extends JpaRepository<File, Long> {
    ArrayList<File> findFilesByPostId(Long postId);
}
