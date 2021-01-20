package com.avelircraft.models.stats;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stats_players")
@AssociationOverride(name = "user", joinColumns= @JoinColumn(name = "username", referencedColumnName="realname"))
public class MyUUID extends UUIDAbstract<UUID> implements Serializable {

    @OneToMany(mappedBy = "uuidRef", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PlayTime> playTime;

    @Transactional
    public List<PlayTime> getPlayTime() {
        return playTime;
    }

    public void setPlayTime(List<PlayTime> playTime) {
        this.playTime = playTime;
    }

//    @Type(type="uuid-char")
//    @Override
//    public String getUuid() {
//        return uuid;
//    }
//
//    @Type(type="uuid-char")
//    @Override
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }
}
