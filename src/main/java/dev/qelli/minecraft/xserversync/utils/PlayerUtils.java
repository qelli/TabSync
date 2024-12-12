package dev.qelli.minecraft.xserversync.utils;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.saicone.mcode.bukkit.util.SkullTexture;

import dev.qelli.minecraft.xserversync.XServerSync;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;

public class PlayerUtils {

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
    }

    public static String getSkinByPlayerName(String playerName) {
        try {
            return SkullTexture.craftHead().textureFrom(playerName);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
