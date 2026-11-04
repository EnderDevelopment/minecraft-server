package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Component message = Component.text("Welcome to the server, ")
                .color(NamedTextColor.GOLD)
                .append(Component.text(player.getName()).color(NamedTextColor.WHITE))
                .append(Component.text("!").color(NamedTextColor.GOLD));
        player.sendMessage(message);

        event.joinMessage(Component.text(player.getName()).color(NamedTextColor.YELLOW)
                .append(Component.text(" joined the game.").color(NamedTextColor.YELLOW)));

        if (plugin.getSpawnLocation() != null && !player.hasPlayedBefore()) {
            Bukkit.getScheduler().runTask(plugin, () -> player.teleport(plugin.getSpawnLocation()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.quitMessage(Component.text(event.getPlayer().getName()).color(NamedTextColor.YELLOW)
                .append(Component.text(" left the game.").color(NamedTextColor.YELLOW)));
    }
}
