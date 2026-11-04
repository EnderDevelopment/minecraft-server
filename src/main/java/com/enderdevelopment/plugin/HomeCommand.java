package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeCommand implements CommandExecutor {

    private final HomeManager homeManager;
    private final Main plugin;

    public HomeCommand(HomeManager homeManager, Main plugin) {
        this.homeManager = homeManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use /home.").color(NamedTextColor.RED));
            return true;
        }

        List<String> homeNames = homeManager.getHomeNames(player.getUniqueId());
        if (homeNames.isEmpty()) {
            player.sendMessage(Component.text("You have no homes set yet. Use /sethome <name>.").color(NamedTextColor.GOLD));
            return true;
        }

        String homeName;
        if (args.length == 0) {
            if (homeNames.contains("home")) {
                homeName = "home";
            } else {
                player.sendMessage(Component.text("Please specify a home name: /home <name>").color(NamedTextColor.GOLD));
                player.sendMessage(Component.text("Your homes: " + String.join(", ", homeNames)).color(NamedTextColor.AQUA));
                return true;
            }
        } else {
            homeName = args[0].toLowerCase();
            if (!homeNames.contains(homeName)) {
                player.sendMessage(Component.text("No home named \"" + homeName + "\" found.").color(NamedTextColor.RED));
                player.sendMessage(Component.text("Your homes: " + String.join(", ", homeNames)).color(NamedTextColor.AQUA));
                return true;
            }
        }

        var location = homeManager.getHome(player.getUniqueId(), homeName);
        if (location == null) {
            player.sendMessage(Component.text("That home could not be loaded (world missing?).").color(NamedTextColor.RED));
            return true;
        }

        player.teleport(location);
        player.sendMessage(Component.text("Teleported to home \"" + homeName + "\"!").color(NamedTextColor.GREEN));
        return true;
    }
}
