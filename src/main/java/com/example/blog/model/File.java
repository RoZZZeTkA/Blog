package com.example.blog.model;

import javax.persistence.*;

@Entity
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

    public String getUrl() {
        return url;
    }
}
