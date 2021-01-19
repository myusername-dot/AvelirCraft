package com.avelircraft.models.stats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "stats_playtime")
@AttributeOverride(name = "last", column = @Column(name = "last_updated"))
@AssociationOverride(name = "uuid", joinColumns= @JoinColumn(name = "player", referencedColumnName="uuid"))
public class PlayTime extends StatsAbstract<MyUUID> implements Serializable {

    @Column(name = "amount")
    private Integer amount;

    public PlayTime() {
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayTime playTime = (PlayTime) o;
        return amount.equals(playTime.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), amount);
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

//    @Type(type="uuid-char")
//    @Override
//    public UUID getUuid() {
//        return uuid;
//    }
//
//    @Type(type="uuid-char")
//    @Override
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }
}
