package com.avelircraft.models.stats;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class PlayTimeId implements Serializable {

    private UUID player;

    private UUID world;

    PlayTimeId(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayTimeId that = (PlayTimeId) o;
        return player.equals(that.player) &&
                world.equals(that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, world);
    }
}
