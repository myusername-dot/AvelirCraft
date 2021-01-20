package com.avelircraft.models.stats;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class StatsAbstract<T> implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", referencedColumnName="uuid", updatable = false, insertable = false)
    protected T uuidRef;

    @Column(name="last")
    protected String last;

    public StatsAbstract(){}

    public T getUuidRef() {
        return uuidRef;
    }

    public String getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatsAbstract)) return false;
        StatsAbstract<?> that = (StatsAbstract<?>) o;
        return uuidRef.equals(that.uuidRef) &&
                last.equals(that.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidRef, last);
    }

    public void setUuidRef(T uuidRef) {
        this.uuidRef = uuidRef;
    }

    public void setLast(String last) {
        this.last = last;
    }
}