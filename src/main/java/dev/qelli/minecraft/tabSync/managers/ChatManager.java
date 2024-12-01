package dev.qelli.minecraft.tabSync.managers;

import java.util.List;
import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.messenger.models.PlayerModel;

public class ChatManager {
    private TabSync plugin;

    public ChatManager(TabSync plugin) {
        this.plugin = plugin;
    }

    public void sendPlayerJoin(PlayerModel player) {
        plugin.getServer().broadcastMessage(player.getName() + " joined the game");
    }

    public void sendPlayersJoin(List<PlayerModel> players) {
        for (PlayerModel player : players) {
            sendPlayerJoin(player);
        }
    }

    public void sendPlayerQuit(PlayerModel player) {
        plugin.getServer().broadcastMessage(player.getName() + " left the game");
    }

    public void sendPlayersQuit(List<PlayerModel> players) {
        for (PlayerModel player : players) {
            sendPlayerQuit(player);
        }
    }

}
