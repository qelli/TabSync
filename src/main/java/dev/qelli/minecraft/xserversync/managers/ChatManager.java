package dev.qelli.minecraft.xserversync.managers;

import java.util.List;
import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;
import dev.qelli.minecraft.xserversync.utils.ChatUtils;

public class ChatManager {
    private XServerSync plugin;

    public ChatManager(XServerSync plugin) {
        this.plugin = plugin;
    }

    public void sendPlayerJoin(PlayerModel player) {
        plugin.getServer().broadcastMessage(
            ChatUtils.withColor(
                plugin.getConfig().getString("messages.player_join").replace("%player%", player.getName())
            )
        );
    }

    public void sendPlayersJoin(List<PlayerModel> players) {
        for (PlayerModel player : players) {
            sendPlayerJoin(player);
        }
    }

    public void sendPlayerQuit(PlayerModel player) {
        plugin.getServer().broadcastMessage(
            ChatUtils.withColor(
                plugin.getConfig().getString("messages.player_quit").replace("%player%", player.getName())
            )
        );
    }

    public void sendPlayersQuit(List<PlayerModel> players) {
        for (PlayerModel player : players) {
            sendPlayerQuit(player);
        }
    }

}
