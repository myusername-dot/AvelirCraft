package com.avelircraft.models.stats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "stats_playtime")
@AttributeOverride(name = "last", column = @Column(name = "last_updated"))
@AssociationOverride(name = "uuid", joinColumns= @JoinColumn(name = "player", referencedColumnName="uuid"))
public class PlayTime extends StatsAbstract<UUID> implements Serializable {

    @Column(name = "amount")
    private Double amount;

    public PlayTime() {
    }

    public Double getAmount() {
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
