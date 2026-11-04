package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomesCommand implements CommandExecutor {

    private final HomeManager homeManager;

    public HomesCommand(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can list homes.").color(NamedTextColor.RED));
            return true;
        }

        List<String> homeNames = homeManager.getHomeNames(player.getUniqueId());
        if (homeNames.isEmpty()) {
            player.sendMessage(Component.text("You have no homes set yet. Use /sethome <name>.").color(NamedTextColor.GOLD));
            return true;
        }

        player.sendMessage(Component.text("Your homes (" + homeNames.size() + "): " + String.join(", ", homeNames)).color(NamedTextColor.AQUA));
        return true;
    }
}
