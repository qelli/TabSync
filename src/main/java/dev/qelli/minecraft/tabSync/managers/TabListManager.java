package dev.qelli.minecraft.tabSync.managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.utils.ProtocolLibUtil;

public class TabListManager {

    private TabSync plugin;

    public TabListManager(TabSync plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(Player player) {
        ProtocolLibUtil.sendPacketToAll(
            ProtocolLibUtil.createTabListAddPacket(
                player.getUniqueId(),
                player.getName(),
                getSkin(player.getUniqueId())
            )
        );
    }
    public void addPlayer(UUID uuid, String name) {
        ProtocolLibUtil.sendPacketToAll(
            ProtocolLibUtil.createTabListAddPacket(
                uuid,
                name,
                getSkin(uuid)
            )
        );
    }
    public void removePlayer(Player player) {
        // Remove player from tab list
    }
    public void removePlayer(UUID uuid) {

    }

    // TODO: I may need some sleep -- move somewhere else??
    private String getSkin(UUID uuid) {
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
                plugin.getLogger().warning("Failed to get skin for UUID: " + uuid + ". Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            plugin.getLogger().severe("Error while getting skin for UUID: " + uuid);
            e.printStackTrace();
        }
        return null;
    }
}
