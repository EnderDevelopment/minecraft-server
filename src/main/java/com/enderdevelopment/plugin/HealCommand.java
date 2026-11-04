package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

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
            sender.sendMessage(Component.text("Please specify a player to heal.").color(NamedTextColor.RED));
            return true;
        }

        target.setHealth(target.getHealthScale());
        target.setFoodLevel(20);
        target.setSaturation(20f);
        target.setFireTicks(0);
        target.sendMessage(Component.text("You have been healed!").color(NamedTextColor.GREEN));
        sender.sendMessage(Component.text("Healed ").color(NamedTextColor.GREEN)
                .append(Component.text(target.getName()).color(NamedTextColor.GOLD)));
        return true;
    }
}
