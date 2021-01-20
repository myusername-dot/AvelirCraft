package com.avelircraft.models.stats;

import com.avelircraft.models.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="mmocore_playerdata")
@AttributeOverride(name="last", column=@Column(name="last_login"))
public class MMOCore extends StatsAbstract<Role> implements Serializable {

    @Id
    @Column(name="uuid")
    private String uuid;

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

    public void setClas(String clas) {
        this.clas = clas;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MMOCore mmoCore = (MMOCore) o;
        return uuid.equals(mmoCore.uuid) &&
                clas.equals(mmoCore.clas) &&
                lvl.equals(mmoCore.lvl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), uuid, clas, lvl);
    }
}
