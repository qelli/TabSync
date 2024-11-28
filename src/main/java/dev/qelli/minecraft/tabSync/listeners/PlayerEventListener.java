package dev.qelli.minecraft.tabSync.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.messenger.models.PlayerModel;

public class PlayerEventListener implements Listener {
    
    TabSync plugin;

    public PlayerEventListener(TabSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getInstanceManager().playerJoined(PlayerModel.fromPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        plugin.getInstanceManager().playerQuit(PlayerModel.fromPlayer(event.getPlayer()));
    }

}
