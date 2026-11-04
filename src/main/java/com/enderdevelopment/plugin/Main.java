package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Location spawnLocation;
    private HomeManager homeManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadSpawnFromConfig();
        homeManager = new HomeManager(this);

        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("fly").setExecutor(new FlyCommand());

        getCommand("sethome").setExecutor(new SetHomeCommand(homeManager));
        getCommand("home").setExecutor(new HomeCommand(homeManager, this));
        getCommand("delhome").setExecutor(new DelHomeCommand(homeManager));
        getCommand("homes").setExecutor(new HomesCommand(homeManager));

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        getLogger().log(Level.INFO, "EnderServer has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "EnderServer has been disabled.");
    }

    public static Main getInstance() {
        return instance;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
        getConfig().set("spawn.world", location.getWorld().getName());
        getConfig().set("spawn.x", location.getX());
        getConfig().set("spawn.y", location.getY());
        getConfig().set("spawn.z", location.getZ());
        getConfig().set("spawn.yaw", (double) location.getYaw());
        getConfig().set("spawn.pitch", (double) location.getPitch());
        saveConfig();
    }

    private void loadSpawnFromConfig() {
        String worldName = getConfig().getString("spawn.world");
        if (worldName == null || Bukkit.getWorld(worldName) == null) {
            spawnLocation = null;
            return;
        }
        double x = getConfig().getDouble("spawn.x");
        double y = getConfig().getDouble("spawn.y");
        double z = getConfig().getDouble("spawn.z");
        float yaw = (float) getConfig().getDouble("spawn.yaw");
        float pitch = (float) getConfig().getDouble("spawn.pitch");
        spawnLocation = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
}
