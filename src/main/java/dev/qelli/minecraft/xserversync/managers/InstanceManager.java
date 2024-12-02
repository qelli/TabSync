package dev.qelli.minecraft.xserversync.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.saicone.delivery4j.MessageChannel;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.messenger.Messenger;
import dev.qelli.minecraft.xserversync.messenger.models.PlayersUpdateMessageModel;
import dev.qelli.minecraft.xserversync.utils.Constants;
import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;

public class InstanceManager {

    private Messenger messenger;
    
    private static boolean initialized = false;
    private static Map<String, List<PlayerModel>> instances = new HashMap<>();

    XServerSync plugin;

    public InstanceManager(XServerSync plugin) {
        this.plugin = plugin;
        this.messenger = new Messenger(plugin);
    }

    public void init() {
        instances.put(getInstanceName(), new ArrayList<>());
        initListeners();
        messenger.start();
    }

    /*
     * Used to tell other instances a new one is alive
     */
    public void load() {
        sendSyncMessage(Constants.Actions.Instances.Sync);
    }

    public void stop() {
        sendSyncMessage(Constants.Actions.Instances.Sync_EXIT);
        messenger.clear();
        messenger.close();
    }

    public String getInstanceName() {
        return plugin.getConfig().getString("instance.name") + "-" + plugin.getConfig().getInt("instance.id");
    }

    public void initListeners() {
        MessageChannel channel = messenger.subscribe(Constants.Channels.Main).consume((channelName, messages) -> {
            PlayersUpdateMessageModel message = PlayersUpdateMessageModel.fromString(messages[0]);
            plugin.getLogger().info("Instance " + message.getInstanceName() + " said " + message.getAction());
            switch(message.getAction()) {
                case Constants.Actions.Players.Message:
                    // TODO: Implement
                    break;
                case Constants.Actions.Players.Join:
                    instances.get(message.getInstanceName()).addAll(message.getPlayers());
                    plugin.getTabListManager().addFakePlayers(message.getPlayers());
                    plugin.getChatManager().sendPlayersJoin(message.getPlayers());
                    break;
                case Constants.Actions.Players.Quit:
                    instances.get(message.getInstanceName()).removeAll(message.getPlayers());
                    plugin.getTabListManager().updateFakePlayers(getPlayersFromOtherInstances());
                    plugin.getChatManager().sendPlayersQuit(message.getPlayers());
                    break;
                case Constants.Actions.Instances.Sync:
                    instances.put(message.getInstanceName(), message.getPlayers());
                    sendSyncMessage(Constants.Actions.Instances.Sync_OK);
                    break;
                case Constants.Actions.Instances.Sync_OK:
                    instances.put(message.getInstanceName(), message.getPlayers());
                    break;
                case Constants.Actions.Instances.Sync_EXIT:
                    instances.remove(message.getInstanceName());
                    break;
            }
        });
        channel.cache(true);
        initialized = true;
    }

    /*
     * When a player joins the current instance
     * must be added to the current instance
     * and send a message to others
     */
    public void playerJoined(PlayerModel player) {
        instances.get(getInstanceName()).addAll(List.of(player));
        PlayersUpdateMessageModel message = new PlayersUpdateMessageModel();
        message.setInstanceName(getInstanceName());
        message.setAction(Constants.Actions.Players.Join);
        message.setPlayers(List.of(player));
        messenger.send(Constants.Channels.Main, message.toString());
    }

    /*
     * When a player quits the current instance
     * must be removed from the current instance
     * and send a message to others
     */
    public void playerQuit(PlayerModel player) {
        instances.get(getInstanceName()).removeAll(List.of(player));
        PlayersUpdateMessageModel message = new PlayersUpdateMessageModel();
        message.setInstanceName(getInstanceName());
        message.setAction(Constants.Actions.Players.Quit);
        message.setPlayers(List.of(player));
        messenger.send(Constants.Channels.Main, message.toString());
    }

    public void playerChat(PlayerModel player, String message) {
        // TODO: Implement
    }






    private void sendSyncMessage(String action) {
        PlayersUpdateMessageModel message = new PlayersUpdateMessageModel();
        message.setInstanceName(getInstanceName());
        message.setAction(action);
        List<PlayerModel> players = new ArrayList<>();
        if (action.equals(Constants.Actions.Instances.Sync) || action.equals(Constants.Actions.Instances.Sync_OK)) {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                players.add(PlayerModel.fromPlayer(player));
            }
        }
        message.setPlayers(players);
        messenger.send(Constants.Channels.Main, message.toString());
    }




    private List<PlayerModel> getPlayersFromOtherInstances() {
        List<PlayerModel> players = new ArrayList<>();
        for (String key : instances.keySet()) {
            if (!key.equals(getInstanceName())) {
                players.addAll(instances.get(key));
            }
        }
        return players;
    }

    public void log() {
        plugin.getLogger().info(" ====================================");
        for (String key : instances.keySet()) {
            plugin.getLogger().info("Instance: " + key);
            for (PlayerModel player : instances.get(key)) {
                plugin.getLogger().info("=> Player: " + player.getName());
            }
        }
        plugin.getLogger().info(" ====================================");
    }

    public boolean isReady() {
        return initialized && messenger.isEnabled();
    }

}
