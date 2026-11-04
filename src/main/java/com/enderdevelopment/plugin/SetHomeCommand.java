package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetHomeCommand implements CommandExecutor {

    private final HomeManager homeManager;

    public SetHomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can set a home.").color(NamedTextColor.RED));
            return true;
        }

        List<String> existing = homeManager.getHomeNames(player.getUniqueId());
        if (args.length == 0) {
            if (existing.size() >= 1) {
                player.sendMessage(Component.text("You can set a named home with /sethome <name>.").color(NamedTextColor.GOLD));
                return true;
            }
            homeManager.setHome(player.getUniqueId(), "home", player.getLocation());
            player.sendMessage(Component.text("Home set at your current location!").color(NamedTextColor.GREEN));
            return true;
        }

        String homeName = args[0].toLowerCase();
        if (!homeName.matches("[a-z0-9_-]{1,32}")) {
            player.sendMessage(Component.text("Invalid home name. Use letters, numbers, dashes or underscores.").color(NamedTextColor.RED));
            return true;
        }

        int limit = pluginLimit(player);
        if (existing.size() >= limit && !existing.contains(homeName)) {
            player.sendMessage(Component.text("You have reached your home limit of " + limit + ".").color(NamedTextColor.RED));
            return true;
        }

        homeManager.setHome(player.getUniqueId(), homeName, player.getLocation());
        player.sendMessage(Component.text("Home \"" + homeName + "\" set at your current location!").color(NamedTextColor.GREEN));
        return true;
    }

    private int pluginLimit(Player player) {
        if (player.hasPermission("enderserver.homes.unlimited")) {
            return Integer.MAX_VALUE;
        }
        if (player.hasPermission("enderserver.homes.5")) {
            return 5;
        }
        return 1;
    }
}
