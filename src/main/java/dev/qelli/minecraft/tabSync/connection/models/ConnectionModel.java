package dev.qelli.minecraft.tabSync.connection.models;

public abstract class ConnectionModel {
    public abstract boolean isConnected();
    public abstract void start();
    public abstract void stop();
}
