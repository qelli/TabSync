package dev.qelli.minecraft.tabSync.storage.impl;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.storage.Storage;

public class RedisStorage extends Storage {

    

    private boolean ready = false;

    public RedisStorage(TabSync plugin) {     
        plugin.getLogger().info("Redis storage is not implemented yet.");
    }

    @Override
    public boolean isReady() {
        return ready;
    }
}
