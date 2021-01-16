package com.avelircraft.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;

    @Column(name = "date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "message")
    private String message;

    public Comment() {
    }

    public Comment(User user, News news, String message) {
        this.user = user;
        this.news = news;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user.getRealname() +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }

    @Transactional
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
