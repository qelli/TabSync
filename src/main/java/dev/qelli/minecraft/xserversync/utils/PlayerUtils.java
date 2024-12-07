package dev.qelli.minecraft.xserversync.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import dev.qelli.minecraft.xserversync.XServerSync;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;

public class PlayerUtils {

    public static OfflinePlayer getPlayerByUUID(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return player;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer != null) {
            return offlinePlayer;
        }
        return null;
    }

    public static String getPlayerGroup(UUID uuid) {
        switch(XServerSync.getInstance().getPermissionsProvider()) {
            case "luckperms":
                return getPlayerGroupFromLuckPerms(uuid);
            default:
                return "default";
        }
    }

    private static String getPlayerGroupFromLuckPerms(UUID uuid) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        if (luckPerms != null) {
            UserManager userManager = luckPerms.getUserManager();
            if (userManager != null) {
                User luckpermsUser = userManager.getUser(uuid);
                if (luckpermsUser != null) {
                    String primaryGroup = luckpermsUser.getPrimaryGroup();
                    if (primaryGroup != null) {
                        return primaryGroup;
                    }
                }
            }
        }
        return "default";
    }

    public static String getPlayerDisplayName(Player player) {
        return player.getName();
        // if (isTabAPIEnabled) {
            // User luckpermsUser = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
            // if (luckpermsUser == null) {
            //     return "[NO_LUCKPERMS] " + player.getName();
            // }
            // TabAPI.getInstance().getPlayer(null)
            // String test = ChatUtils.withColor(PlaceholderAPI.setPlaceholders(player, "%xserversync_fullplayername%"));
            // System.out.println("testing with " + test);
            // TabAPI.getInstance().getPlaceholderManager().getPlaceholder("")
            // return test;
            // // Tab
            // for (TabPlayer tabPlayer : TabAPI.getInstance().getOnlinePlayers()) {
            // if(tabPlayer == null) {
            // System.out.println(("why is this null????"));
            // continue;
            // }
            // System.out.println("DEMOING TabAPI");
            // System.out.println("getCustomPrefix()" +
            // TabAPI.getInstance().getTabListFormatManager().getCustomPrefix(tabPlayer));
            // System.out.println("getCustomName()" +
            // TabAPI.getInstance().getTabListFormatManager().getCustomName(tabPlayer));
            // System.out.println("getCustomSuffix()" +
            // TabAPI.getInstance().getTabListFormatManager().getCustomSuffix(tabPlayer));
            // System.out.println("TabPlayer.toString(): " + tabPlayer.toString());
            // System.out.println("TabPlayer.getName(): " + tabPlayer.getName());
            // System.out.println("TabPlayer.getExpectedProfileName(): " +
            // tabPlayer.getExpectedProfileName());
            // }
            // TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
            // if (tabPlayer == null) {
            // System.out.println("tabPlayer is null...");
            // return player.getName();
            // }
            // System.out.println("Using TabAPI");
            // System.out.println("TabPlayer.toString(): " + tabPlayer.toString());
            // System.out.println("TabPlayer.getName(): " + tabPlayer.getName());
            // System.out.println("TabPlayer.getExpectedProfileName(): " +
            // tabPlayer.getExpectedProfileName());
            // return tabPlayer.getExpectedProfileName();
            // return "";
        // }
        // return player.getName();
    }

    // TODO: Fix lol
    public static String getPlayerSkin(Player player) {
        return "";
        // try {
        // return
        // player.getPlayerProfile().getTextures().getSkin().getContent().toString();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return "";
    }

}
