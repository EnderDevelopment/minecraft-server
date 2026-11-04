package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        Player target;
        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(Component.text("Player not found.").color(NamedTextColor.RED));
                return true;
            }
        } else if (sender instanceof Player player) {
            target = player;
        } else {
            sender.sendMessage(Component.text("Please specify a player.").color(NamedTextColor.RED));
            return true;
        }

        boolean flying = !target.getAllowFlight();
        target.setAllowFlight(flying);
        if (!flying) {
            target.setFlying(false);
        }
        String state = flying ? "enabled" : "disabled";
        target.sendMessage(Component.text("Flight " + state + ".").color(flying ? NamedTextColor.GREEN : NamedTextColor.RED));
        sender.sendMessage(Component.text("Flight " + state + " for ")
                .append(Component.text(target.getName()).color(NamedTextColor.GOLD)));
        return true;
    }
}
