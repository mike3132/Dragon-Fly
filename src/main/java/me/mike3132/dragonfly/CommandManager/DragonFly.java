package me.mike3132.dragonfly.CommandManager;

import me.mike3132.dragonfly.ChatManager.Messages;
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

    public static List<UUID> flyingAdmins = new ArrayList<>();

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
                            Messages.sendMessage(player, "No-Reload-Permission");
                            return true;
                        }
                        sender.sendMessage(Main.chatColor("&0[&bDragon &fFly&0] " + "&6Config reloaded in &2" + String.valueOf(System.currentTimeMillis() - 1 + " &6ms")));
                        Main.plugin.reloadConfig();
                        break;
                    case "HELP":
                        Messages.sendMessage(player, "Help-Header");
                        Messages.sendMessage(player, "Help-A");
                        Messages.sendMessage(player, "Help-B");
                        Messages.sendMessage(player, "Help-C");
                        Messages.sendMessage(player, "Help-D");
                        Messages.sendMessage(player, "Help-E");
                        Messages.sendMessage(player, "Help-Footer");
                        break;
                    case "ADMINFLY":
                        if (!player.hasPermission("dragonFly.AdminFly")) {
                            Messages.sendMessage(player, "No-AdminFly-Permission");
                            return true;
                        }
                        if (!flyingAdmins.contains(player.getUniqueId())) {
                            flyingAdmins.add(player.getUniqueId());
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            Messages.sendMessage(player, "Admin-Flight-Enabled");
                        } else {
                            flyingAdmins.remove(player.getUniqueId());
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            Messages.sendMessage(player, "Admin-Flight-Disabled");
                        }
                        break;
                    case "TOGGLE":
                        if (!player.hasPermission("dragonFly.Toggle.Others")) {
                            Messages.sendMessage(player, "No-Toggle-Others-Permission");
                            return true;
                        }
                        if (args.length != 1) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target != null) {
                                if (!Fly.flyingPlayers.contains(target.getUniqueId()) || Fly.freeFlyingPlayers.contains(target.getUniqueId())) {
                                    Messages.playerPlaceholderMessage(player, "Target-Not-Flying", target.getName());
                                } else {
                                    Messages.playerPlaceholderMessage(target, "Target-Flight-Disabled", target.getName());
                                    Fly.flyingPlayers.remove(target.getUniqueId());
                                    Fly.freeFlyingPlayers.remove(target.getUniqueId());
                                    target.setAllowFlight(false);
                                    target.setFlying(false);
                                }
                            } else {
                                Messages.sendMessage(player, "Target-Not-Found");
                            }
                        } else {
                            player.sendMessage("Help-Trigger");
                        }
                        break;
                }
            }
        } else {
            Messages.sendMessage(player, "Help-Trigger");
        }

        return true;
    }
}
