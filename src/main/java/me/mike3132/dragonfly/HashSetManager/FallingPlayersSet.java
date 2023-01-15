package me.mike3132.dragonfly.HashSetManager;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class FallingPlayersSet {

    private static final HashSet<UUID> fallingPlayers = new HashSet<>();

    /**
     *
     * @return The current set of flying players.
     */
    public static HashSet<UUID> getFallingPlayers() {
        return fallingPlayers;
    }

    /**
     *
     * @param player The player who is being added to the set.
     */
    public static void addFallingPlayers(UUID player) {
        getFallingPlayers().add(player);
    }

    /**
     *
     * @param player The player who is being removed from the set.
     */
    public static void removeFallingPlayers(UUID player) {
        getFallingPlayers().remove(player);
    }


}
