package me.mike3132.dragonfly.CommandManager;

import me.mike3132.dragonfly.ChatManager.ChatMessages;
import me.mike3132.dragonfly.FlyManager.CostFlyManager;
import me.mike3132.dragonfly.FlyManager.FreeFlyManager;
import me.mike3132.dragonfly.HashSetManager.CostFlyingSet;
import me.mike3132.dragonfly.HashSetManager.FreeFlyingSet;
import me.mike3132.dragonfly.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Fly implements CommandExecutor {


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
                ChatMessages.sendMessage(player, "No-Permission");
                return false;
            }
            if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
                ChatMessages.sendMessage(player, "Not-In-Survival");
                return false;
            }
            boolean blacklistEnabled = Main.plugin.getConfig().getBoolean("World-Blacklist-Enabled");
            String worldName = player.getWorld().getName();
            List<String> blacklistedWorlds = new ArrayList<>();
            for (String world : Main.plugin.getConfig().getStringList("World-Blacklist")) {
                blacklistedWorlds.add(world);
            }

            if (player.hasPermission("dragonFly.NoFuel")) {
                if (!FreeFlyingSet.getFlyingPlayers().contains(player.getUniqueId())) {
                    if (blacklistEnabled && blacklistedWorlds.contains(worldName)) {
                        ChatMessages.sendMessage(player, "Fly-Enabled-Blacklisted-World");
                        return true;
                    }
                    FreeFlyManager.onAddPlayer(player);
                    return false;
                }
                FreeFlyManager.onRemovePlayer(player);

            } else {
                if (player.hasPermission("dragonFly.Fuel")) {
                    if (!player.getInventory().contains(Material.valueOf(Main.plugin.getConfig().getString("Fuel")))) {
                        ChatMessages.fuelPllaceholderMessage(player, "No-Fuel-In-Inventory", Main.plugin.getConfig().getString("Fuel"));
                        return false;
                    }
                    if (!CostFlyingSet.getFlyingPlayers().contains(player.getUniqueId())) {
                        if (blacklistEnabled && blacklistedWorlds.contains(worldName)) {
                            ChatMessages.sendMessage(player, "Fly-Enabled-Blacklisted-World");
                            return true;
                        }
                        CostFlyManager.onAddPlayer(player);
                        return false;
                    }
                    CostFlyManager.onRemovePlayer(player);
                } else {
                    ChatMessages.sendMessage(player, "No-Fly-Extra-Permission");
                }
            }
        }
        return true;
    }
}
