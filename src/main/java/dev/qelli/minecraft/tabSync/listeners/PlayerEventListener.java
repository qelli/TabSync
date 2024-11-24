package dev.qelli.minecraft.tabSync.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.qelli.minecraft.tabSync.TabSync;

public class PlayerEventListener implements Listener {
    
    TabSync plugin;

    public PlayerEventListener(TabSync plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getMessenger().sendTabListJoinUpdate(
            event.getPlayer().getUniqueId().toString(),
            event.getPlayer().getName()
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        plugin.getMessenger().sendTabListQuitUpdate(
            event.getPlayer().getUniqueId().toString(),
            event.getPlayer().getName()
        );
    }

}
