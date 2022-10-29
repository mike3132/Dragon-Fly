package me.mike3132.dragonfly.EventHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FallEvent implements Listener {

    public static List<UUID> fallingPlayers = new ArrayList<>();

    public FallEvent() {
    }

    @EventHandler
    public void onPlayerFall(EntityDamageEvent ede) {
        if (ede.getCause() != EntityDamageEvent.DamageCause.FALL)return;
        if (fallingPlayers.contains(ede.getEntity().getUniqueId())) {
            ede.setCancelled(true);
            fallingPlayers.remove(ede.getEntity().getUniqueId());
        }
    }
}
