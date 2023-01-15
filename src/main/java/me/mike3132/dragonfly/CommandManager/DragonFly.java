package me.mike3132.dragonfly.CommandManager;

import me.mike3132.dragonfly.ChatManager.ChatMessages;
import me.mike3132.dragonfly.ConfigManager.ConfigCreator;
import me.mike3132.dragonfly.EventHandler.WorldChangeEvent;
import me.mike3132.dragonfly.HashSetManager.CostFlyingSet;
import me.mike3132.dragonfly.HashSetManager.FreeFlyingSet;
import me.mike3132.dragonfly.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DragonFly implements CommandExecutor {

    private final static List<UUID> flyingAdmins = new ArrayList<>();

    public DragonFly() {
        Main.plugin.getCommand("DragonFly").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("&0[&bDragon &fFly&0] &6Commands cannot be ran from the console");
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            if (player.hasPermission("dragonFly.Admin")) {
                switch (args[0].toUpperCase()) {
                    case "RELOAD":
                        if (!player.hasPermission("dragonFly.Reload")) {
                            ChatMessages.sendMessage(player, "No-Reload-Permission");
                            return false;
                        }
                        sender.sendMessage(Main.chatColor("&0[&bDragon &fFly&0] " + "&6Config reloaded in &2" + String.valueOf(System.currentTimeMillis() - 1 + " &6ms")));
                        Main.plugin.reloadConfig();
                        break;
                    case "HELP":
                        ChatMessages.sendMessage(player, "Help-Header");
                        ChatMessages.sendMessageNoPrefix(player, "Help-A");
                        ChatMessages.sendMessageNoPrefix(player, "Help-B");
                        ChatMessages.sendMessageNoPrefix(player, "Help-C");
                        ChatMessages.sendMessageNoPrefix(player, "Help-D");
                        ChatMessages.sendMessageNoPrefix(player, "Help-E");
                        ChatMessages.sendMessage(player, "Help-Footer");
                        break;
                    case "ADMINFLY":
                        if (!player.hasPermission("dragonFly.AdminFly")) {
                            ChatMessages.sendMessage(player, "No-AdminFly-Permission");
                            return false;
                        }
                        if (!flyingAdmins.contains(player.getUniqueId())) {
                            flyingAdmins.add(player.getUniqueId());
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            ChatMessages.sendMessage(player, "Admin-Flight-Enabled");
                            return false;
                        }
                        flyingAdmins.remove(player.getUniqueId());
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        ChatMessages.sendMessage(player, "Admin-Flight-Disabled");
                        break;
                    case "TOGGLE":
                        if (!player.hasPermission("dragonFly.Toggle.Others")) {
                            ChatMessages.sendMessage(player, "No-Toggle-Others-Permission");
                            return false;
                        }
                        if (args.length != 1) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                ChatMessages.sendMessage(player, "Target-Not-Found");
                                return false;
                            }
                            if (!CostFlyingSet.getFlyingPlayers().contains(target.getUniqueId()) &&
                                    !FreeFlyingSet.getFlyingPlayers().contains(target.getUniqueId())){
                                ChatMessages.playerPlaceholderMessage(player, "Target-Not-Flying", target.getName());
                                return false;
                            }
                            ChatMessages.playerPlaceholderMessage(player, "Target-Flight-Disabled", target.getName());
                            CostFlyingSet.removeFlyingPlayers(target.getUniqueId());
                            FreeFlyingSet.removeFlyingPlayers(target.getUniqueId());
                            target.setAllowFlight(false);
                            target.setFlying(false);

                        } else {
                            player.sendMessage("Help-Trigger");
                        }
                        break;
                }
            }
        } else {
            ChatMessages.sendMessage(player, "Help-Trigger");
        }

        return true;
    }
}
