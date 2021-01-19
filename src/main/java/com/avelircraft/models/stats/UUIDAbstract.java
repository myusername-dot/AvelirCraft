package com.avelircraft.models.stats;

import com.avelircraft.models.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class UUIDAbstract<T> implements Serializable {

    @Id
    @Column(name = "uuid")
    protected T uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName="username")
    protected User user;

    public UUIDAbstract(){}

    public UUIDAbstract(T uuid, User user) {
        this.uuid = uuid;
        this.user = user;
    }

    public T  getUuid() {
        return uuid;
    }

    public void setUuid(T  uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
