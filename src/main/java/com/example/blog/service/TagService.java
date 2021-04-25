package com.example.blog.service;

import com.example.blog.model.Tag;
import com.example.blog.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    public TagRepository tagRepository;

    public Tag findTagByValue(String value){
        return tagRepository.findTagByValue(value);
    }

    public Tag addTag(String value) {
        Tag tag = tagRepository.findTagByValue(value);
        if(tag != null){
            return tag;
        }
        return tagRepository.save(new Tag(value));
    }
}
