package me.mike3132.dragonfly.EventHandler;

import me.mike3132.dragonfly.ChatManager.ChatMessages;
import me.mike3132.dragonfly.FlyManager.CostFlyManager;
import me.mike3132.dragonfly.FlyManager.FreeFlyManager;
import me.mike3132.dragonfly.HashSetManager.CostFlyingSet;
import me.mike3132.dragonfly.HashSetManager.FreeFlyingSet;
import me.mike3132.dragonfly.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldChangeEvent implements Listener {

    /**
     *
     * @param wc The world change event, I check to see if the player is inside either the free or cost sets,
     *           if so I force set them to fly and set their allow flight to true. I then check to see if the
     *           black listed worlds is enabled in the config.yml, and then I check to see if the world the player is
     *           entering is in the list. If so I remove them from the corresponding sets and use the Fly managers to
     *           remove their fly and send them a message saying they have entered a blacklisted world.
     */
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent wc) {
        Player player = wc.getPlayer();
        String worldName = player.getWorld().getName();
        boolean blacklistEnabled = Main.plugin.getConfig().getBoolean("World-Blacklist-Enabled");

        List<String> blacklistedWorlds = new ArrayList<>();
        for (String world : Main.plugin.getConfig().getStringList("World-Blacklist")) {
            blacklistedWorlds.add(world);
        }

        if (FreeFlyingSet.getFlyingPlayers().contains(player.getUniqueId())) {
            player.setAllowFlight(true);
            player.setFlying(true);
            if (blacklistEnabled && blacklistedWorlds.contains(worldName)) {
                FreeFlyManager.onRemovePlayer(player);
                ChatMessages.sendMessage(player, "Enter-Blacklisted-World");
            }
        }

        if (CostFlyingSet.getFlyingPlayers().contains(player.getUniqueId())) {
            player.setAllowFlight(true);
            player.setFlying(true);
            if (blacklistEnabled && blacklistedWorlds.contains(worldName)) {
                CostFlyManager.onRemovePlayer(player);
                ChatMessages.sendMessage(player, "Enter-Blacklisted-World");
            }
        }

    }
}
