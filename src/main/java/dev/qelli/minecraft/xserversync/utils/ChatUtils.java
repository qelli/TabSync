package dev.qelli.minecraft.xserversync.utils;

import org.bukkit.ChatColor;

public class ChatUtils {
    
    public static String withColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
