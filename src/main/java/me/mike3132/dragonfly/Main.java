package me.mike3132.dragonfly;

import me.mike3132.dragonfly.ChatManager.ChatMessages;
import me.mike3132.dragonfly.CommandManager.AdminFly;
import me.mike3132.dragonfly.CommandManager.DragonFly;
import me.mike3132.dragonfly.CommandManager.Fly;
import me.mike3132.dragonfly.ConfigManager.ConfigCreator;
import me.mike3132.dragonfly.EventHandler.FallEvent;
import me.mike3132.dragonfly.EventHandler.GameModeChangeEvent;
import me.mike3132.dragonfly.EventHandler.WorldChangeEvent;
import me.mike3132.dragonfly.FlyManager.CostFlyManager;
import me.mike3132.dragonfly.FlyManager.FallManager;
import me.mike3132.dragonfly.HashSetManager.CostFlyingSet;
import me.mike3132.dragonfly.HashSetManager.FallingPlayersSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    /**
     * @param chatColor The string of the chat color that is being used.
     * @return Returns the corresponding Bukkit color code to the & code that is used.
     */
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
        Bukkit.getPluginManager().registerEvents(new WorldChangeEvent(), this);
        Bukkit.getPluginManager().registerEvents(new GameModeChangeEvent(), this);

        // Command Loader
        registerFly();
        registerDragonFly();
        registerAdminFly();

        // Config Loader
        saveDefaultConfig();
        getConfig();
        ConfigCreator.MESSAGES.create();

        /**
         * Global scheduler for all players, Checks to see if they have use /fly and if so starts taking fuel.
         * If they run out of fuel it swaps their hashset and notifies them.
         */
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!CostFlyingSet.getFlyingPlayers().contains(player.getUniqueId())) return;
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.getType().equals(Material.valueOf(this.getConfig().getString("Fuel")))) {
                        item.setAmount(item.getAmount() -1);
                        player.updateInventory();
                        break;
                    }
                    if (!player.getInventory().contains(Material.valueOf(this.getConfig().getString("Fuel")))) {
                        ChatMessages.sendMessage(player, "Out-Of-Fuel");
                        ChatMessages.sendMessage(player, "Free-Falling-Message");
                        CostFlyManager.onRemovePlayer(player);
                        FallingPlayersSet.addFallingPlayers(player.getUniqueId());
                        FallManager.onFallingPlayer(player);
                        break;
                    }
                }
            }

        }, 20L, plugin.getConfig().getInt("Fuel-Time"));
    }


    /**
     * Registers the fly command
     */
    public void registerFly() {
        new Fly();
    }

    /**
     * Registers the DragonFly command (This handles all admin commands)
     */
    public void registerDragonFly() {
        new DragonFly();
    }

    public void registerAdminFly() {
        new AdminFly();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(chatColor("&0[&bDragon &fFly&0]" + "&4&lDISABLED"));
    }
}