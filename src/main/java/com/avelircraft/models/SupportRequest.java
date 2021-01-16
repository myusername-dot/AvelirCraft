package com.avelircraft.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "support_requests")
public class SupportRequest implements Comparable<SupportRequest> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "topic")
    private String topic;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SupportComment> supportComments;

    public SupportRequest() {
    }

    public SupportRequest(Integer userId, String topic) {
        this.userId = userId;
        this.topic = topic;
        status = "filed";
    }

    public Long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

    public String getTopic() {
        return topic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transactional(readOnly = true)
    public List<SupportComment> getSupportComments() {
        return supportComments;
    }

    @Transactional
    public void addSupportComment(SupportComment supportComment) {
        if(supportComments == null)
            supportComments = new ArrayList<>();
        supportComments.add(supportComment);
    }

    @Override
    public int compareTo(SupportRequest b) { // field, processed, close
        SupportRequest a = this;
        if (!a.status.equals(b.status)){
            if (a.status.equals("filed")) return 1;
            if (a.status.equals("close")) return -1;
            if (b.status.equals("filed")) return -1;
            return 1;
        }
        return a.date.compareTo(b.date);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setSupportComments(List<SupportComment> supportComments) {
        this.supportComments = supportComments;
    }
}
