package com.avelircraft.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "support_comments")
public class SupportComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private SupportRequest request;

    @Column(name = "date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "message")
    private String message;

    @Transient
    private boolean adminIcon;

    public SupportComment() {
    }

    @PostLoad
    private void setAdminIcon() {
        adminIcon = user.getRoles().stream()
                .anyMatch(role -> role.getRole()
                        .matches("admin"));
    }

    public SupportComment(User user, SupportRequest request, String message) {
        this.user = user;
        this.request = request;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public SupportRequest getRequest() {
        return request;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public boolean getAdminIcon() {
        return adminIcon;
    }
}
