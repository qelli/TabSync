package dev.qelli.minecraft.tabSync.connection;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.connection.models.ConnectionModel;
import dev.qelli.minecraft.tabSync.connection.models.impl.DefaultConnection;
import dev.qelli.minecraft.tabSync.connection.models.impl.RedisConnection;

public class Connection {

    private TabSync plugin;
    private ConnectionModel connection;

    public Connection(TabSync plugin) {
        this.plugin = plugin;
    }

    public void init() {
        connection = connect();
        connection.start();
    }

    private ConnectionModel connect() {
        switch(plugin.getConfig().getString("connection.type")) {
            case "redis":
                return new RedisConnection(plugin);
            default:
                return new DefaultConnection();
        }
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

}
