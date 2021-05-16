package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long postId;
    private Long userId;
    private int value;

    @ManyToOne
    @JoinColumn(name = "postId", insertable = false, updatable = false)
    private Post post;

    public Mark(){}

    public Mark(Long postId, Long userId, int value) {
        this.postId = postId;
        this.userId = userId;
        this.value = value;
    }

    @JsonIgnore
    public Post getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", postId=" + postId +
                ", userId=" + userId +
                ", value=" + value +
                '}';
    }
}
