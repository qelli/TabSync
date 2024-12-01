package dev.qelli.minecraft.xserversync.messenger;

import com.saicone.delivery4j.AbstractMessenger;
import com.saicone.delivery4j.Broker;
import com.saicone.delivery4j.broker.RedisBroker;

import dev.qelli.minecraft.xserversync.XServerSync;

public class Messenger extends AbstractMessenger {

    XServerSync plugin;

    public Messenger(XServerSync plugin) {
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
