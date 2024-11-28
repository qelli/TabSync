package dev.qelli.minecraft.tabSync.managers;

import org.bukkit.Bukkit;

import dev.qelli.minecraft.tabSync.TabSync;

public class ChatManager {
    private TabSync plugin;

    public ChatManager(TabSync plugin) {
        this.plugin = plugin;
    }

    public void sendPlayerJoin(String name) {
        Bukkit.getServer().broadcastMessage(name + " joined the game");
    }

    public void sendPlayerQuit(String name) {
        Bukkit.getServer().broadcastMessage(name + " left the game");
    }

}
