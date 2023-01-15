package me.mike3132.dragonfly.EventHandler;

import me.mike3132.dragonfly.HashSetManager.CostFlyingSet;
import me.mike3132.dragonfly.HashSetManager.FreeFlyingSet;
import me.mike3132.dragonfly.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class GameModeChangeEvent implements Listener {

    /**
     *
     * @param pcm The player change game mode event, Checks to make sure the gamemode that is being changed into is
     *            equal to creative and then checks to see if the creative mode check is enabled in the config.yml
     *            if so I check to see if the world is in a string list of creative worlds in the config.yml
     *            If all checks have passed I remove the player from both the free and cost sets, and allow creative
     *            flight to take over.
     */
    @EventHandler
    public void onPlayerChangeMode(PlayerGameModeChangeEvent pcm) {
        Player player = pcm.getPlayer();
        String worldName = player.getWorld().getName();
        boolean creativeCheckEnabled = Main.plugin.getConfig().getBoolean("Creative-Worlds-Enabled");

        List<String> creativeWorlds = new ArrayList<>();
        for (String worlds : Main.plugin.getConfig().getStringList("Creative-Worlds")) {
            creativeWorlds.add(worlds);
        }
        if (pcm.getNewGameMode() == GameMode.CREATIVE) {
            if (creativeCheckEnabled && creativeWorlds.contains(worldName)) {
                if (CostFlyingSet.getFlyingPlayers().contains(player.getUniqueId()) ||
                        FreeFlyingSet.getFlyingPlayers().contains(player.getUniqueId())) {
                    CostFlyingSet.removeFlyingPlayers(player.getUniqueId());
                    FreeFlyingSet.removeFlyingPlayers(player.getUniqueId());
                }
            }
        }

    }

}
