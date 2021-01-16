package com.avelircraft.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "guide")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "link")
    private String link;

    @Column(name = "header")
    private String header;

    @Column(name = "description")
    private String desc;

    @Column(name = "tags")
    private String tags;

    @Column(name = "date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "views")
    private Long views;

    public Guide incrementViews() {
        views++;
        return this;
    }

    public Guide() {
        this.link = "https://www.youtube.com/embed/HCGluweMz60";
        this.header = "пусто";
        this.desc = "пусто";
        this.tags = "";
        views = 0L;
    }

    public Guide(String link, String header, String desc, String tags) {
        this.link = link;
        this.header = header;
        this.desc = desc;
        this.tags = tags;
        views = 0L;
    }

    public Integer getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
