package com.example.blog.controller;

import com.example.blog.model.Mark;
import com.example.blog.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/mark")
public class MarkController {

    @Autowired
    private MarkService markService;

    @PostMapping("/add")
    public ResponseEntity<Mark> addMark(@RequestParam(value = "postId")Long postId, @RequestParam(value = "value")int value){
        return new ResponseEntity<>(markService.addMark(postId, value), HttpStatus.OK);
    }
}
