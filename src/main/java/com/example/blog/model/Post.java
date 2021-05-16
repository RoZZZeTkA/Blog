package com.example.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Long userId;
    private Date date;
    private String title;

    @Column(length = 32767)
    private String value;

    @ManyToMany
    @JoinTable(
        name = "post_tag",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> postTags;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Mark> postMarks;

    public Post() {}

    public Post(Long id, Long userId, String value) {
        this.id = id;
        this.userId = userId;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", postTags=" + postTags +
                ", postMarks=" + postMarks +
                '}';
    }
}
