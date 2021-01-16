package com.avelircraft.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @Column(name = "user_id")
    private Integer userId;
    private String type;
    private byte[] img;

    public Image(){}

    public Image(Integer id, String type, byte[] img) {
        this.userId = id;
        this.type = type;
        this.img = img;
    }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + userId +
                ", type='" + type + '\'' +
                '}';
    }
}
