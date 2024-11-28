package dev.qelli.minecraft.tabSync.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.saicone.delivery4j.MessageChannel;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.messenger.Messenger;
import dev.qelli.minecraft.tabSync.messenger.models.PlayersUpdateMessageModel;
import dev.qelli.minecraft.tabSync.utils.Constants;
import dev.qelli.minecraft.tabSync.messenger.models.PlayerModel;

public class InstanceManager {

    private Messenger messenger;
    
    private static boolean initialized = false;
    private static Map<String, List<PlayerModel>> instances = new HashMap<>();

    TabSync plugin;

    public InstanceManager(TabSync plugin) {
        this.plugin = plugin;
        this.messenger = new Messenger(plugin);
    }

    public void init() {
        instances.put(getInstanceName(), new ArrayList<>());
        messenger.start();
        initListeners();
        // TODO: Find the best place to call load() as the instances are not getting updated when getting enabled
    }

    public void load() {
        sendSyncMessage(false);
    }

    public void stop() {
        messenger.clear();
        messenger.close();
        // TODO: Send sync message of instance disconnecting
    }

    public String getInstanceName() {
        return plugin.getConfig().getString("instance.name") + "-" + plugin.getConfig().getInt("instance.id");
    }

    public void initListeners() {
        MessageChannel channel = messenger.subscribe(Constants.PLAYER_UPDATES_CHANNEL_NAME).consume((channelName, messages) -> {
            PlayersUpdateMessageModel message = PlayersUpdateMessageModel.fromString(messages[0]);
            switch(message.getAction()) {
                case "player_message":
                // plugin.getChatManager().sendPlayerMessage();
                    break;
                case "player_joined":
                    instances.get(message.getInstanceName()).addAll(message.getPlayers());
                    // plugin.getTabListManager().addPlayers(message.getPlayers());
                    for (PlayerModel player : message.getPlayers()) {
                        plugin.getChatManager().sendPlayerJoin(player.getName());
                    }
                    // TODO: Check instance to send fake player packets to the user
                    break;
                case "player_quit":
                    for(PlayerModel player : message.getPlayers()) {
                        plugin.getChatManager().sendPlayerQuit(player.getName());
                    }
                    break;
                case "player_sync":
                    instances.put(message.getInstanceName(), message.getPlayers());
                    sendSyncMessage(true);
                    break;
                case "player_sync_ok":
                    instances.put(message.getInstanceName(), message.getPlayers());
                    break;
            }
        });
        channel.cache(true);
        initialized = true;
    }

    /*
     * When a player joins the current instance
     */
    public void playerJoined(PlayerModel player) {
        instances.get(getInstanceName()).addAll(List.of(player));
        PlayersUpdateMessageModel message = new PlayersUpdateMessageModel();
        message.setInstanceName(getInstanceName());
        message.setAction("player_joined");
        message.setPlayers(List.of(player));
        messenger.send(Constants.PLAYER_UPDATES_CHANNEL_NAME, message.toString());
    }

    /*
     * When a player quits the current instance
     */
    public void playerQuit(PlayerModel player) {
        instances.get(getInstanceName()).removeAll(List.of(player));
        PlayersUpdateMessageModel message = new PlayersUpdateMessageModel();
        message.setInstanceName(getInstanceName());
        message.setAction("player_quit");
        message.setPlayers(List.of(player));
        messenger.send(Constants.PLAYER_UPDATES_CHANNEL_NAME, message.toString());
    }






    private void sendSyncMessage(boolean isSyncResponse) {
        PlayersUpdateMessageModel message = new PlayersUpdateMessageModel();
        message.setInstanceName(getInstanceName());
        message.setAction(isSyncResponse ? "player_sync_ok" : "player_sync");
        List<PlayerModel> players = new ArrayList<>();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            players.add(PlayerModel.fromPlayer(player));
        }
        message.setPlayers(players);
        messenger.send(Constants.PLAYER_UPDATES_CHANNEL_NAME, message.toString());
    }




    // public List<PlayerModel> getPlayersExcept(String instance) {
    //     List<PlayerModel> players = new ArrayList<>();
    //     for (String key : instances.keySet()) {
    //         if (!key.equals(instance)) {
    //             players.addAll(instances.get(key));
    //         }
    //     }
    //     return players;
    // }

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
