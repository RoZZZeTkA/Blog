package com.example.blog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long postId;
    private String url;

    public File() {}

    public File(Long postId, String url){
        this.postId = postId;
        this.url = url;
    }
}
