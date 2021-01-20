package com.avelircraft.models.stats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "stats_playtime")
@IdClass(PlayTimeId.class)
@AttributeOverride(name = "last", column = @Column(name = "last_updated"))
@AssociationOverride(name = "uuidRef", joinColumns=
@JoinColumn(name = "player", referencedColumnName="uuid", updatable = false, insertable = false))
public class PlayTime extends StatsAbstract<MyUUID> implements Serializable {

    @Id
    @Column(name = "world")
    private UUID world;

    @Id
    @Column(name="player")
    private UUID player;

    @Column(name = "amount")
    private Integer amount;

    public PlayTime() {
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "PlayTime{" +
                "amount=" + amount +
                '}';
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayTime playTime = (PlayTime) o;
        return world.equals(playTime.world) &&
                player.equals(playTime.player) &&
                amount.equals(playTime.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), world, player, amount);
    }
}
