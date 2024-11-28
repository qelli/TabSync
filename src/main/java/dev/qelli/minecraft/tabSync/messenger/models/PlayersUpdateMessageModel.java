package dev.qelli.minecraft.tabSync.messenger.models;

import java.util.List;

import org.json.JSONObject;

public class PlayersUpdateMessageModel {

    private String instanceName;
    private String action;
    private List<PlayerModel> players;

    public static PlayersUpdateMessageModel fromString(String message) {
        JSONObject json = new JSONObject(message);
        PlayersUpdateMessageModel model = new PlayersUpdateMessageModel();
        model.setInstanceName(json.getString("instanceName"));
        model.setAction(json.getString("action"));
        model.setPlayers(PlayerModel.fromJSONArray(json.getJSONArray("players")));
        return model;
    }

    public String toString() {
        return new JSONObject()
                .put("instanceName", instanceName)
                .put("action", action)
                .put("players", players)
                .toString();
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public String getAction() {
        return action;
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public boolean isFromInstance(String instanceName) {
        return this.instanceName.equals(instanceName);
    }

}
