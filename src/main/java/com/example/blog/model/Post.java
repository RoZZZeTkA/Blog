package com.example.blog.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Tag> getPostTags() {
        return postTags;
    }

    public void setPostTags(Set<Tag> postTags) {
        this.postTags = postTags;
    }

    public List<Mark> getPostMarks() {
        return postMarks;
    }

    public void setPostMarks(List<Mark> postMarks) {
        this.postMarks = postMarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
