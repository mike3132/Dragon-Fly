package me.mike3132.dragonfly.HashSetManager;

import java.util.HashSet;
import java.util.UUID;

public class CostFlyingSet {

    private final static HashSet<UUID> flyingPlayers = new HashSet<>();

    /**
     *
     * @return The current set of flying players.
     */
    public static HashSet<UUID> getFlyingPlayers() {
        return flyingPlayers;
    }

    /**
     *
     * @param player The player who is being added to the set.
     */
    public static void addFlyingPlayers(UUID player) {
        getFlyingPlayers().add(player);
    }

    /**
     *
     * @param player The player who is being removed from the set.
     */
    public static void removeFlyingPlayers(UUID player) {
        getFlyingPlayers().remove(player);
    }
}
