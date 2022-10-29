package me.mike3132.dragonfly.CommandManager;

import me.mike3132.dragonfly.ChatManager.Messages;
import me.mike3132.dragonfly.EventHandler.FallEvent;
import me.mike3132.dragonfly.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Fly implements CommandExecutor {


    public static List<UUID> flyingPlayers = new ArrayList<>();
    public static List<UUID> freeFlyingPlayers = new ArrayList<>();


    public Fly() {
        Main.plugin.getCommand("Fly").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use fly commands");
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (!player.hasPermission("dragonFly.Use")) {
                Messages.sendMessage(player, "No-Permission");
            }
            if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                Messages.sendMessage(player, "Not-In-Survival");
            }
            if (player.hasPermission("dragonFly.NoFuel")) {
                if (!freeFlyingPlayers.contains(player.getUniqueId())) {
                    freeFlyingPlayers.add(player.getUniqueId());
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    Messages.sendMessage(player, "Flight-Enabled");
                } else {
                    freeFlyingPlayers.remove(player.getUniqueId());
                    FallEvent.fallingPlayers.add(player.getUniqueId());
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    Messages.sendMessage(player, "Flight-Disabled");
                }
            } else {
                if (player.hasPermission("dragonFly.Fuel")) {
                    if (!player.getInventory().contains(Material.valueOf(Main.plugin.getConfig().getString("Fuel")))) {
                        Messages.fuelPllaceholderMessage(player, "No-Fuel-In-Inventory", Main.plugin.getConfig().getString("Fuel"));
                    } else {
                        if (!flyingPlayers.contains(player.getUniqueId())) {
                            flyingPlayers.add(player.getUniqueId());
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            Messages.sendMessage(player, "Flight-Enabled");
                        } else {
                            flyingPlayers.remove(player.getUniqueId());
                            FallEvent.fallingPlayers.add(player.getUniqueId());
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            Messages.sendMessage(player, "Flight-Disabled");
                        }
                    }
                } else {
                    Messages.sendMessage(player, "No-Fly-Extra-Permission");
                }
            }
        }
        return true;
    }
}
