package com.avelircraft.models.stats;

import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "stats_players")
@AssociationOverride(name = "user", joinColumns= @JoinColumn(name = "username", referencedColumnName="realname"))
public class UUID extends UUIDAbstract implements Serializable {

    @OneToOne(mappedBy = "uuid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private PlayTime playTime;

    @Transactional
    public PlayTime getPlayTime() {
        return playTime;
    }

    public void setPlayTime(PlayTime playTime) {
        this.playTime = playTime;
    }
}
