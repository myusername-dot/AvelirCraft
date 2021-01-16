package com.avelircraft.models.stats;

import com.avelircraft.models.Role;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="mmocore_playerdata")
@AttributeOverride(name="last", column=@Column(name="last_login"))
public class MMOCore extends StatsAbstract<Role> implements Serializable {

    @Column(name="class")
    private String clas;

    @Column(name="level")
    private Integer lvl;

    public MMOCore(){}

    public String getClas() {
        return clas;
    }

    public Integer getLvl() {
        return lvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MMOCore)) return false;
        MMOCore mmoCore = (MMOCore) o;
        return clas.equals(mmoCore.clas) &&
                lvl.equals(mmoCore.lvl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clas, lvl);
    }
}
