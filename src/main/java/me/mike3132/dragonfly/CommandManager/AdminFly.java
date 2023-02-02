package me.mike3132.dragonfly.CommandManager;

import me.mike3132.dragonfly.ChatManager.ChatMessages;
import me.mike3132.dragonfly.HashSetManager.AdminFlyingSet;
import me.mike3132.dragonfly.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminFly implements CommandExecutor {

    public AdminFly() {
        Main.plugin.getCommand("AdminFly").setExecutor(this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use fly commands");
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (!player.hasPermission("dragonFly.AdminFly")) {
                ChatMessages.sendMessage(player, "No-AdminFly-Permission");
                return false;
            }
            if (!AdminFlyingSet.getFlyingAdmins().contains(player.getUniqueId())) {
                AdminFlyingSet.addFlyingAdmins(player.getUniqueId());
                player.setAllowFlight(true);
                player.setFlying(true);
                ChatMessages.sendMessage(player, "Admin-Flight-Enabled");
                return false;
            }
            AdminFlyingSet.removeFlyingAdmins(player.getUniqueId());
            player.setAllowFlight(false);
            player.setFlying(false);
            ChatMessages.sendMessage(player, "Admin-Flight-Disabled");
        }
        return false;
    }
}
