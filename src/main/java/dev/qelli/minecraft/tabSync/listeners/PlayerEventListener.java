package dev.qelli.minecraft.tabSync.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.managers.Tab;

public class PlayerEventListener implements Listener{
    
    TabSync plugin;

    public PlayerEventListener(TabSync plugin) {
        this.plugin = plugin;
    }
    

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("SI JALA");
        // plugin.getTabManager().test("lololo", UUID.randomUUID());
        plugin.getTabManager().addFakePlayer("Fakeeee");
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event) {
        
    }

}
