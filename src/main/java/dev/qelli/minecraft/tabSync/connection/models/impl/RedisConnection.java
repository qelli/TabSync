package dev.qelli.minecraft.tabSync.connection.models.impl;

import com.saicone.delivery4j.broker.RedisBroker;
import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.connection.models.ConnectionModel;

public class RedisConnection extends ConnectionModel {
    
    private TabSync plugin;
    // private Jedis jedis;
    private RedisBroker broker;
    // private Broker broker;

    public RedisConnection(TabSync plugin) {
        this.plugin = plugin;
        broker = RedisBroker.of(plugin.getConfig().getString("redis.url"));
    }

    public void start() {
        // jedis.connect();
        broker.start();
        // broker.start();
    }

    public void stop() {
        // jedis.close();
        broker.close();
    }

    public boolean isConnected() {
        return broker.isEnabled();
        // return jedis.isConnected();// && broker.isEnabled();
    }
}
