package com.avelircraft.models.stats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class StatsAbstract<T> implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", referencedColumnName="uuid")
    private T uuid;

    @Column(name="last")
    private String last;

    public StatsAbstract(){}

    public T getUuid() {
        return uuid;
    }

    public String getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsAbstract)) return false;
        StatsAbstract<?> that = (StatsAbstract<?>) o;
        return uuid.equals(that.uuid) &&
                last.equals(that.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, last);
    }

    public void setUuid(T uuid) {
        this.uuid = uuid;
    }

    public void setLast(String last) {
        this.last = last;
    }
}