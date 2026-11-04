package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class DelHomeCommand implements CommandExecutor {

    private final HomeManager homeManager;

    public DelHomeCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can delete a home.").color(NamedTextColor.RED));
            return true;
        }

        List<String> homeNames = homeManager.getHomeNames(player.getUniqueId());
        if (homeNames.isEmpty()) {
            player.sendMessage(Component.text("You have no homes to delete.").color(NamedTextColor.GOLD));
            return true;
        }

        String homeName;
        if (args.length == 0) {
            if (homeNames.contains("home")) {
                homeName = "home";
            } else {
                player.sendMessage(Component.text("Please specify a home name: /delhome <name>").color(NamedTextColor.GOLD));
                player.sendMessage(Component.text("Your homes: " + String.join(", ", homeNames)).color(NamedTextColor.AQUA));
                return true;
            }
        } else {
            homeName = args[0].toLowerCase();
            if (!homeNames.contains(homeName)) {
                player.sendMessage(Component.text("No home named \"" + homeName + "\" found.").color(NamedTextColor.RED));
                return true;
            }
        }

        homeManager.removeHome(player.getUniqueId(), homeName);
        player.sendMessage(Component.text("Home \"" + homeName + "\" deleted.").color(NamedTextColor.GREEN));
        return true;
    }
}
