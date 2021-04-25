package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.Tag;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    public PostRepository postRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public TagService tagService;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAllPosts() { return postRepository.findAll(); }

    public Post findPostById(Long id) { return postRepository.findPostById(id); }

    public Set<Post> findPostsByTags(String tags){
        String[] tagArray = tags.split(",");
        Set<Post> posts = new HashSet<>();
        posts.addAll(tagService.findTagByValue(tagArray[0]).getTagPosts());
        for(int i = 1; i < tagArray.length; i++){
            posts.retainAll(tagService.findTagByValue(tagArray[i]).getTagPosts());
        }
        return posts;
    }

    public Post addPost(Post post, String tags){
        post.setUserId(userService.findUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        Set<Tag> tagSet = new HashSet<>();
        String[] tagArray = tags.split(", ");
        for(String tag: tagArray){
            tagSet.add(tagService.addTag(tag));
        }
        post.setPostTags(tagSet);
        return postRepository.save(post);
    }

    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public Post findPostByCurrentUserAndTitle(String title) {
        return postRepository.findPostByUserIdAndTitle(userService.findUserByNickname(
                SecurityContextHolder.getContext().getAuthentication().getName()).getId(), title);
    }

    public List<Post> findPostsByUserId(Long userId){
        return postRepository.findPostsByUserId(userId);
    }
}
