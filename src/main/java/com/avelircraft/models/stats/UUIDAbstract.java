package com.avelircraft.models.stats;

import com.avelircraft.models.User;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class UUIDAbstract implements Serializable {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName="username")
    private User user;

    public UUIDAbstract(){}

    public UUIDAbstract(String uuid, User user) {
        this.uuid = uuid;
        this.user = user;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
