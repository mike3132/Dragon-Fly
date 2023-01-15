package me.mike3132.dragonfly.EventHandler;

import me.mike3132.dragonfly.HashSetManager.FallingPlayersSet;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallEvent implements Listener {


    /**
     *
     * @param ede The entity damage event, Checks to see if the entity is a player and if the damage cause is fall damage.
     *            if So then I cancel the event, and remove them from the Falling players set.
     */
    @EventHandler
    public void onPlayerFall(EntityDamageEvent ede) {
        if (!(ede.getEntity() instanceof Player)) return;
        Player player = (Player) ede.getEntity();
        if (ede.getCause() != EntityDamageEvent.DamageCause.FALL)return;
        if (FallingPlayersSet.getFallingPlayers().contains(player.getUniqueId())) {
            ede.setCancelled(true);
            FallingPlayersSet.removeFallingPlayers(player.getUniqueId());
        }
    }
}
