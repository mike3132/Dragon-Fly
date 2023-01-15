package me.mike3132.dragonfly.ChatManager;

import me.mike3132.dragonfly.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ChatMessages {
    /**
     *
     * @param player This is the player who is sent the message.
     * @param key The message that is being sent.
     */
    public static void sendMessage(Player player, String key) {
        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     *
     * @param player The player who is being sent the message.
     * @param key The message that is being sent.
     * @param fuel The material that is being used as fuel.
     */
    public static void fuelPllaceholderMessage(Player player, String key, String fuel) {
        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        message = message.replace("%Fuel%", fuel);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     *
     * @param player The player that is sent the message.
     * @param key The message that is sent.
     * @param target This returns the target players name.
     */
    public static void playerPlaceholderMessage(Player player, String key, String target) {
        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        message = message.replace("%playerName%", target);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     *
     * @param sender The sender of the command. (Can be player or console)
     * @param key The message that is sent
     */
    public static void sendMessageNoPrefix(CommandSender sender, String key) {
        File messagesConfig = new File(Main.plugin.getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages." + key, "message");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
