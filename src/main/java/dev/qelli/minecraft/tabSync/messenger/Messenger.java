package dev.qelli.minecraft.tabSync.messenger;

import com.saicone.delivery4j.AbstractMessenger;
import com.saicone.delivery4j.Broker;
import com.saicone.delivery4j.broker.RedisBroker;

import dev.qelli.minecraft.tabSync.TabSync;

public class Messenger extends AbstractMessenger {

    TabSync plugin;

    public Messenger(TabSync plugin) {
        this.plugin = plugin;
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

}
