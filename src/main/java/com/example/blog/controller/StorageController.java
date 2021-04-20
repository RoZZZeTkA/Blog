package com.example.blog.controller;

import com.example.blog.model.File;
import com.example.blog.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/post/{id}")
    public ResponseEntity<List<File>> getUrlsByPostId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(storageService.findFilesByPostId(id), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file")MultipartFile file, @RequestParam(value = "title")String title){
        return new ResponseEntity<>(storageService.uploadFile(file, title), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageService.deleteFile(fileName), HttpStatus.OK);
    }
}
