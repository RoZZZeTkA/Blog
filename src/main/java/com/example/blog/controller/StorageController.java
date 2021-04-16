package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file")MultipartFile file, @RequestParam(value = "userId")Long userId, @RequestParam(value = "title")String title){
        return new ResponseEntity<>(storageService.uploadFile(file, userId, title), HttpStatus.OK);
    }
}
