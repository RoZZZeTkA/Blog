package com.example.blog.service;

import com.example.blog.model.File;
import com.example.blog.model.Post;
import com.example.blog.model.Tag;
import com.example.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public TagService tagService;

    @Autowired
    public StorageService storageService;

    @Autowired
    public MarkService markService;

    public List<Post> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        posts.sort(Comparator.comparing(Post::getId).reversed());
        return posts;
    }

    public Post findPostById(Long id) {
        Post post = postRepository.findPostById(id);
        post.setPostTags(post.getPostTags().stream().sorted(Comparator.comparing(Tag::getId)).collect(Collectors.toCollection(LinkedHashSet::new)));
        return post;
    }

    public Set<Post> findPostsByTags(String tags) {
        String[] tagArray = tags.split("[,.;\\s]+");
        Set<Post> posts = new HashSet<>();
        Tag tag = tagService.findTagByValue(tagArray[0]);
        if(tag != null)
            posts.addAll(tag.getTagPosts());
        for(int i = 1; i < tagArray.length; i++){
            tag = tagService.findTagByValue(tagArray[i]);
            if(tag != null)
                posts.retainAll(tag.getTagPosts());
        }
        return posts.stream().sorted(Comparator.comparing(Post::getId).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public List<Post> findPostByQuery(String query) {
        List<Post> posts = new ArrayList<>(postRepository.findPostsByTitleLike("%" + query + "%"));
        posts.sort(Comparator.comparing(Post::getId).reversed());
        Set<Post> postSet = findPostsByTags(query);
        postSet.removeAll(posts);
        posts.addAll(postSet);
        return posts;
    }

    public Post addPost(Post post, String tags) {
        Long userId = userService.findUserByNickname(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        if(postRepository.findPostByUserIdAndTitle(userId, post.getTitle()) == null) {
            post.setUserId(userId);
            Set<Tag> tagSet = new HashSet<>();
            String[] tagArray = tags.split("[,.;\\s]+");
            for (String tag : tagArray) {
                tagSet.add(tagService.addTag(tag));
            }
            post.setPostTags(tagSet);
            post.setDate(new Date());
            return postRepository.save(post);
        } else
            throw new IllegalArgumentException("This user already has a post with the same title");
    }

    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deletePost(Long id) {
        List<File> files = storageService.findFilesByPostId(id);
        for(File file: storageService.findFilesByPostId(id)){
            storageService.deleteFile(file.getUrl().substring(55));
        }
        markService.deleteMarksByPostId(id);
        postRepository.deleteById(id);
    }

    public Post findPostByCurrentUserAndTitle(String title) {
        return postRepository.findPostByUserIdAndTitle(userService.findUserByNickname(
                SecurityContextHolder.getContext().getAuthentication().getName()).getId(), title);
    }

    public List<Post> findPostsByUserId(Long userId){
        List<Post> posts = postRepository.findPostsByUserId(userId);
        posts.sort(Comparator.comparing(Post::getId).reversed());
        return posts;
    }
}
