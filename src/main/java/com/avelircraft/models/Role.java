package com.avelircraft.models;

import com.avelircraft.models.stats.MMOCore;
import com.avelircraft.models.stats.PlayTime;
import com.avelircraft.models.stats.UUIDAbstract;

import javax.persistence.*;

import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "luckperms_players")
public class Role extends UUIDAbstract<String> implements Serializable {

    @Column(name = "primary_group")
    private String role;

    @OneToOne(mappedBy = "uuid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MMOCore mmoCore;

    public Role() {
    }

    public Role(User user) {
        super( System.currentTimeMillis() + "default", user);
        role = "default";
    }

    public Role(User user, String role) {
        super(user.getUsername() + "-" + System.currentTimeMillis(), user);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

    @Transactional
    public MMOCore getMmoCore() { return mmoCore; }

    public void setMmoCore(MMOCore mmoCore) {
        this.mmoCore = mmoCore;
    }
}
