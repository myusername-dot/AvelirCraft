package com.avelircraft.models.stats;

import com.avelircraft.models.Role;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name="mmocore_playerdata")
@AttributeOverride(name="last", column=@Column(name="last_login"))
public class MMOCore extends StatsAbstract<Role> implements Serializable {

    private static final Map<String, String> classMap;

    static {
        classMap = new HashMap<>();
        // new File("src/main/resources").getAbsolutePath() + "/role_map.txt")
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Role.class.getClassLoader().getResourceAsStream("class_map.txt"))
                , StandardCharsets.UTF_8))) {
            String line;
            int counter = 1;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[ ]", " ");
                line = line.replaceAll("[^a-zA-Zа-яА-Я0-9 ]","");
                String[] couple = line.split(" ", 2);
                if (couple.length == 1)
                    Logger.getGlobal().log(Level.WARNING, "class_map.txt line \"" + line + "\" " + counter +
                            ": pairs of values must be written line by line and separated by a space");
                else if (couple.length == 2)
                    classMap.put(couple[0], couple[1]);
                counter++;
            }
            System.out.println("classMap: " + classMap);
        }
        catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "class_map.txt read error");
            e.printStackTrace();
        }
    }

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

    @Transient
    public String getClassName() {
        String className = classMap.get(this.clas);
        return className == null ? this.clas : className;
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
