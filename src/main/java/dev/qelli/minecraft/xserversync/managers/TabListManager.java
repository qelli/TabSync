package dev.qelli.minecraft.xserversync.managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;
import dev.qelli.minecraft.xserversync.utils.ProtocolLibUtil;

public class TabListManager {

    private XServerSync plugin;

    public TabListManager(XServerSync plugin) {
        this.plugin = plugin;
    }

    public void addFakePlayers(List<PlayerModel> players) {
        ProtocolLibUtil.sendPacketToAll(
            ProtocolLibUtil.createTabListAddPacket(players)
        );
    }

    public void updateFakePlayers(List<PlayerModel> players) {
        ProtocolLibUtil.sendPacketToAll(
            ProtocolLibUtil.createTabListUpdatePacket(players)
        );
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
