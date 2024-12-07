package dev.qelli.minecraft.xserversync.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;

public class TabPluginEventListener {

    XServerSync plugin;
    
    public TabPluginEventListener(XServerSync plugin) {
        this.plugin = plugin;
        initTabApiListener();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getInstanceManager().playerQuit(PlayerModel.fromPlayer(event.getPlayer()));
    }

    private void initTabApiListener() {
        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class, event -> {
            plugin.getInstanceManager().playerJoined(
                PlayerModel.fromTabPlayer(
                    event.getPlayer(),
                    plugin.getServer().getPlayer(
                        event.getPlayer().getUniqueId()
                    )
                )
            );
        });
    }


}
