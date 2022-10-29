package me.mike3132.dragonfly;

import me.mike3132.dragonfly.ChatManager.Messages;
import me.mike3132.dragonfly.CommandManager.DragonFly;
import me.mike3132.dragonfly.CommandManager.Fly;
import me.mike3132.dragonfly.EventHandler.FallEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {
    public static String chatColor(String chatColor) {
        return ChatColor.translateAlternateColorCodes('&', chatColor);
    }

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getServer().getConsoleSender().sendMessage(chatColor("&0[&bDragon &fFly&0]" + "&2&lENABLED"));

        // Event Listener
        Bukkit.getPluginManager().registerEvents(new FallEvent(), this);

        // Command Loader
        registerFly();
        registerDragonFly();

        // Config Loader
        saveDefaultConfig();
        getConfig();
        createFiles();

        // Task Timer for fly countdown (This runs 20 ticks after plugin loads)
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Fly.flyingPlayers.contains(player.getUniqueId())) {
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item != null && item.getType().equals(Material.valueOf(this.getConfig().getString("Fuel")))) {
                            if (item.getAmount() >= 1) {
                                item.setAmount(item.getAmount() - 1);
                                player.updateInventory();
                                break;
                            }
                        }
                        if (!(player.getInventory().contains(Material.valueOf(this.getConfig().getString("Fuel"))))) {
                            Messages.sendMessage(player, "Out-Of-Fuel");
                            Messages.sendMessage(player, "Free-Falling-Message");
                            Messages.sendMessage(player, "Flight-Disabled");
                            Fly.flyingPlayers.remove(player.getUniqueId());
                            FallEvent.fallingPlayers.add(player.getUniqueId());
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            break;
                        }
                    }
                }
            }
        }, 20L, plugin.getConfig().getInt("Fuel-Time"));

    }

    // Messages.yml file creation
    private File messages;
    private FileConfiguration config;

    private void createFiles() {
        messages = new File(getDataFolder(), "messages.yml");
        if (!messages.exists()) {
            messages.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }
        config = new YamlConfiguration();
        try {
            config.load(messages);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    // Command register
    public void registerFly() {
        new Fly();
    }

    public void registerDragonFly() {
        new DragonFly();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(chatColor("&0[&bDragon &fFly&0]" + "&4&lDISABLED"));
    }
}