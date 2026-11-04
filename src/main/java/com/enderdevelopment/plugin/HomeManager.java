package com.enderdevelopment.plugin;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages per-player home locations, persisted in a homes.yml file.
 */
public class HomeManager {

    private final JavaPlugin plugin;
    private final File homesFile;
    private final YamlConfiguration config;

    public HomeManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.homesFile = new File(plugin.getDataFolder(), "homes.yml");
        this.config = YamlConfiguration.loadConfiguration(homesFile);
    }

    /**
     * Saves a home location for a player under the given home name.
     */
    public void setHome(UUID playerId, String homeName, Location location) {
        String path = playerId + "." + homeName;
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", (double) location.getYaw());
        config.set(path + ".pitch", (double) location.getPitch());
        save();
    }

    /**
     * Returns the home location for a player under the given home name, or null.
     */
    public Location getHome(UUID playerId, String homeName) {
        String path = playerId + "." + homeName;
        String worldName = config.getString(path + ".world");
        if (worldName == null || plugin.getServer().getWorld(worldName) == null) {
            return null;
        }
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");
        return new Location(plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);
    }

    /**
     * Removes a home for a player. Returns true if a home was removed.
     */
    public boolean removeHome(UUID playerId, String homeName) {
        String path = playerId + "." + homeName;
        if (config.get(path + ".world") == null) {
            return false;
        }
        config.set(path, null);
        save();
        return true;
    }

    /**
     * Returns all home names set for a player.
     */
    public java.util.List<String> getHomeNames(UUID playerId) {
        ConfigurationSection section = config.getConfigurationSection(playerId.toString());
        if (section == null) {
            return new java.util.ArrayList<>();
        }
        return new java.util.ArrayList<>(section.getKeys(false));
    }

    /**
     * Returns a map of home name to Location for a player.
     */
    public Map<String, Location> getHomes(UUID playerId) {
        Map<String, Location> homes = new HashMap<>();
        for (String homeName : getHomeNames(playerId)) {
            Location loc = getHome(playerId, homeName);
            if (loc != null) {
                homes.put(homeName, loc);
            }
        }
        return homes;
    }

    private void save() {
        try {
            config.save(homesFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save homes.yml: " + e.getMessage());
        }
    }
}
