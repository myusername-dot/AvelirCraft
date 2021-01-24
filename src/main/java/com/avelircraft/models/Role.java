package com.avelircraft.models;

import com.avelircraft.models.stats.MMOCore;
import com.avelircraft.models.stats.PlayTime;
import com.avelircraft.models.stats.UUIDAbstract;

import javax.persistence.*;

import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

@Entity
@Table(name = "luckperms_players")
public class Role extends UUIDAbstract<String> implements Serializable {

    private static final Map<String, String> roleMap;

    static {
        roleMap = new HashMap<>();
        // new File("src/main/resources").getAbsolutePath() + "/role_map.txt")
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Role.class.getClassLoader().getResourceAsStream("role_map.txt"))
                , StandardCharsets.UTF_8))) {
            String line;
            int counter = 1;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[ ]", " ");
                line = line.replaceAll("[^a-zA-Zа-яА-Я0-9 ]","");
                String[] couple = line.split(" ", 2);
                if (couple.length == 1)
                    Logger.getGlobal().log(Level.WARNING, "role_map.txt line \"" + line + "\" " + counter +
                            ": pairs of values must be written line by line and separated by a space");
                else if (couple.length == 2)
                    roleMap.put(couple[0], couple[1]);
                counter++;
            }
            System.out.println("roleMap: " + roleMap);
        }
        catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "role_map.txt read error");
            e.printStackTrace();
        }
    }

    @Column(name = "primary_group")
    private String role;

    @OneToOne(mappedBy = "uuidRef", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MMOCore mmoCore;

    public Role() {
    }

    public Role(User user) {
        super( System.currentTimeMillis() + "default", user);
        role = "default";
    }

    public Role(User user, String role) {
        super(user.getUsername() + "-" + System.currentTimeMillis(), user);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Transient
    public String getRoleName() {
        String roleName = roleMap.get(role);
        return roleName == null ? role : roleName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getRoleName();
    }

    @Transactional(readOnly = true)
    public MMOCore getMmoCore() { return mmoCore; }

    public void setMmoCore(MMOCore mmoCore) {
        this.mmoCore = mmoCore;
    }
}
