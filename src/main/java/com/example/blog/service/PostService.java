package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    public PostRepository postRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAllPosts() { return postRepository.findAll(); }

    public Post findPostById(Long id) { return postRepository.findPostById(id); }

    public Post addPost(Post post){
        post.setUserId(userService.findUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        return postRepository.save(post);
    }

    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public Post findPostByUserIdAndTitle(Long userId, String title) {
        return postRepository.findPostByUserIdAndTitle(userId, title);
    }

    public List<Post> findPostsByUserId(Long userId){
        return postRepository.findPostsByUserId(userId);
    }
}
