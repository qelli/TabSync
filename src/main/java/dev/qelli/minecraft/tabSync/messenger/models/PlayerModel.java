package dev.qelli.minecraft.xserversync.messenger.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import dev.qelli.minecraft.xserversync.utils.PlayerUtils;

public class PlayerModel {

    private UUID uuid;
    private String name;
    private String skin;

    public static PlayerModel fromPlayer(Player player) {
        return new PlayerModelBuilder()
            .setName(player.getName())
            .setSkin(PlayerUtils.getPlayerSkin(player))
            .setUuid(player.getUniqueId())
            .build();
    }

    public static List<PlayerModel> fromJSONArray(JSONArray jsonArray) {
        List<PlayerModel> playerModels = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            playerModels.add(new PlayerModelBuilder()
                .setUuid(UUID.fromString(jsonObject.getString("uuid")))
                .setName(jsonObject.getString("name"))
                .setSkin(jsonObject.getString("skin"))
                .build());
        }
        return playerModels;
    }

    public static String toString(List<PlayerModel> players) {
        JSONArray jsonArray = new JSONArray();
        for (PlayerModel player : players) {
            jsonArray.put(player.toString());
        }
        return jsonArray.toString();
    }

    public String toString() {
        return new JSONObject()
                .put("uuid", uuid.toString())
                .put("name", name)
                .put("skin", skin)
                .toString();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlayerModel playerModel = (PlayerModel) o;
        if (getUuid().toString().equals(playerModel.getUuid().toString()))
            return true;
        return false;
    }
        
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }
    
}

class PlayerModelBuilder {
    private UUID uuid;
    private String name;
    private String skin;

    public PlayerModelBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public PlayerModelBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PlayerModelBuilder setSkin(String skin) {
        this.skin = skin;
        return this;
    }

    public PlayerModel build() {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setUuid(this.uuid);
        playerModel.setName(this.name);
        playerModel.setSkin(this.skin);
        return playerModel;
    }
}