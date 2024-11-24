package dev.qelli.minecraft.tabSync.messenger;

import com.saicone.delivery4j.AbstractMessenger;
import com.saicone.delivery4j.Broker;
import com.saicone.delivery4j.ChannelConsumer;
import com.saicone.delivery4j.broker.RedisBroker;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.utils.Constants;

public class Messenger extends AbstractMessenger {

    TabSync plugin;

    public Messenger(TabSync plugin) {
        super();
        this.plugin = plugin;
        setExecutor(getExecutor());
    }

    public String getInstanceName() {
        return plugin.getConfig().getString("instance.name") + "." + plugin.getConfig().getInt("instance.id");
    }
    
    @Override
    protected Broker loadBroker() {
        switch(plugin.getConfig().getString("connection.type")) {
            case "redis":
                return RedisBroker.of(plugin.getConfig().getString("connection.url"));
            default:
                return null;
        }
    }

    public void subscribeToTabListUpdates(ChannelConsumer<String[]> consumer) {
        subscribeToOthers(Constants.TAB_CHANNEL_NAME, consumer);
    }

    public void subscribeToChatUpdates(ChannelConsumer<String[]> consumer) {
        subscribeToOthers(Constants.CHAT_CHANNEL_NAME, consumer);
    }

    public void sendTabListJoinUpdate(String uuid, String name) {
        sendToOthers(Constants.TAB_CHANNEL_NAME, Constants.TAB_ACTION_JOIN, uuid, name);
    }

    public void sendTabListQuitUpdate(String uuid, String name) {
        sendToOthers(Constants.TAB_CHANNEL_NAME, Constants.TAB_ACTION_QUIT, uuid, name);
    }

    private void sendToOthers(String channelName, String action, String uuid, String name) {
        send(channelName, getInstanceName(), action, uuid, name);
    }

    private void subscribeToOthers(String channelName, final ChannelConsumer<String[]> consumer) {
        subscribe(channelName).consume((channel, messages) -> {
            plugin.getLogger().info("subscribeToOthers " + messages[0] + " -> " + getInstanceName());
            if(!messages[0].equals(getInstanceName())) {
                consumer.accept(channel, messages);
            }
        });
    }

}
