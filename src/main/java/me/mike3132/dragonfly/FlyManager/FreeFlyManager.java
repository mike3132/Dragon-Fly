package me.mike3132.dragonfly.FlyManager;

import me.mike3132.dragonfly.ChatManager.ChatMessages;
import me.mike3132.dragonfly.HashSetManager.FreeFlyingSet;
import org.bukkit.entity.Player;

public class FreeFlyManager {
    /**
     *
     * @param player The player who is being given flight, They are also sent a message saying their flight was enabled.
     */
    public static void onAddPlayer(Player player) {
        FreeFlyingSet.addFlyingPlayers(player.getUniqueId());
        player.setAllowFlight(true);
        player.setFlying(true);
        ChatMessages.sendMessage(player, "Flight-Enabled");
    }

    /**
     *
     * @param player The player who is being removed from flight, They are also sent a message saying their flight was removed.
     */
    public static void onRemovePlayer(Player player) {
        FreeFlyingSet.removeFlyingPlayers(player.getUniqueId());
        player.setAllowFlight(false);
        player.setFlying(false);
        ChatMessages.sendMessage(player, "Flight-Disabled");
    }
}
