package com.avelircraft.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "header")
    //@NotNull
    private String header;

    @Column(name = "description")
    private String description;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "img_name")
    private String imgName;

    @Column(name = "views")
    private Long views;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public News incrementViews() {
        views++;
        return this;
    }

    public News(){
        header = "";
        message = "";
        description = "";
        id = 0;
        //views = 0l;
        //date =  new Date();
    };

    public News(String header, String description, String message){
        this.header = header;
        this.description = description;
        this.message = message;
        views = 0l;
    }

    public News(String header, String description, String message, String imgName) {
        this.header = header;
        this.description = description;
        this.message = message;
        this.imgName = imgName;
        views = 0l;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgName() { return imgName; }

    public void setImgName(String imgName) { this.imgName = imgName; }

    public Long getViews() {
        return views;
    }

    public Integer getId() {
        return id;
    }

    @Transactional(readOnly = true)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
