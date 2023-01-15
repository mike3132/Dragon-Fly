package me.mike3132.dragonfly.FlyManager;

import me.mike3132.dragonfly.HashSetManager.FallingPlayersSet;
import me.mike3132.dragonfly.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FallManager {

    /**
     *
     * @param player The player who is inside the falling set. This runnable only fires if the player has been added to
     *               the set but has yet to take fall damage. This will then remove them from the set after 1 minute.
     */
    public static void onFallingPlayer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (FallingPlayersSet.getFallingPlayers().contains(player.getUniqueId())) {
                    FallingPlayersSet.removeFallingPlayers(player.getUniqueId());
                }
            }
        }.runTaskLaterAsynchronously(Main.plugin, 1200L);
    }

}
