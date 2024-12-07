package dev.qelli.minecraft.xserversync.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

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

    // This may probably need caching
    public static String getSkin(UUID uuid) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString()))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                JSONArray properties = jsonResponse.getJSONArray("properties");
                for (int i = 0; i < properties.length(); i++) {
                    JSONObject property = properties.getJSONObject(i);
                    if (property.getString("name").equals("textures")) {
                        return property.getString("value");
                    }
                }
            } else {
                XServerSync.getInstance().getLogger().warning("Failed to get skin for UUID: " + uuid + ". Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            XServerSync.getInstance().getLogger().severe("Error while getting skin for UUID: " + uuid);
            e.printStackTrace();
        }
        return "";
    }

}
