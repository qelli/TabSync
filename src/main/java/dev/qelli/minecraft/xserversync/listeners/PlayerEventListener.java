package dev.qelli.minecraft.xserversync.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;

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
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getInstanceManager().playerQuit(PlayerModel.fromPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        plugin.getInstanceManager().playerChat(PlayerModel.fromPlayer(event.getPlayer()), event.getFormat());
    }

}
