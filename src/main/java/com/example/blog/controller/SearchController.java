package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/search")
public class SearchController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getSearchResults(@RequestParam("t") String tags){
        return new ResponseEntity<>(postService.findPostByQuery(tags), HttpStatus.OK);
//        return new ResponseEntity<>(postService.findPostsByTags(tags), HttpStatus.OK);
    }
}
