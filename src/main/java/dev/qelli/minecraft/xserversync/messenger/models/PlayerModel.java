package dev.qelli.minecraft.xserversync.messenger.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import dev.qelli.minecraft.xserversync.utils.ChatUtils;
import dev.qelli.minecraft.xserversync.utils.PlayerUtils;
import dev.qelli.minecraft.xserversync.utils.TabAPIUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import me.neznamy.tab.api.TabPlayer;

public class PlayerModel {

    private UUID uuid;
    private String name;
    private String displayName;
    private String group;
    private Integer listOrder;
    private String skin;

    public static PlayerModel fromPlayer(Player player) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setName(player.getName());
        playerModel.setDisplayName(PlayerUtils.getPlayerDisplayName(player));
        playerModel.setSkin(PlayerUtils.getSkinByPlayerName(player.getName()));
        playerModel.setGroup(PlayerUtils.getPlayerGroup(player.getUniqueId()));
        playerModel.setListOrder(0);
        playerModel.setUuid(player.getUniqueId());
        return playerModel;
    }

    public static PlayerModel fromTabPlayer(TabPlayer tabPlayer, Player bukkitPlayer) {
        PlayerModel player = new PlayerModel();
        player.setUuid(tabPlayer.getUniqueId());
        player.setName(tabPlayer.getName());
        player.setGroup(PlayerUtils.getPlayerGroup(tabPlayer.getUniqueId()));
        // TODO: Move placeholderapi usage to PlayerUtils
        player.setDisplayName(ChatUtils.withColor(PlaceholderAPI.setPlaceholders(bukkitPlayer, TabAPIUtils.getOriginalName(tabPlayer))));
        player.setListOrder(0);
        player.setSkin(PlayerUtils.getSkinByPlayerName(bukkitPlayer.getName()));
        return player;
    }

    public static List<PlayerModel> fromJSONArray(JSONArray jsonArray) {
        List<PlayerModel> playerModels = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            PlayerModel player = new PlayerModel();
            player.setUuid(UUID.fromString(jsonObject.getString("uuid")));
            player.setName(jsonObject.getString("name"));
            player.setGroup(jsonObject.getString("group"));
            player.setDisplayName(jsonObject.getString("displayName"));
            player.setListOrder(jsonObject.getInt("listOrder"));
            player.setSkin(jsonObject.getString("skin"));
            playerModels.add(player);
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
                .put("displayName", displayName)
                .put("group", group)
                .put("listOrder", listOrder)
                .put("skin", skin)
            .toString();
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlayerModel playerModel = (PlayerModel) o;
        // TODO: Do I really need to compare UUIDs as strings?
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
}
