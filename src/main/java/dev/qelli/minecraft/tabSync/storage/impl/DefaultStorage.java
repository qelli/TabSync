package dev.qelli.minecraft.tabSync.storage.impl;

import dev.qelli.minecraft.tabSync.TabSync;
import dev.qelli.minecraft.tabSync.storage.Storage;

public class DefaultStorage extends Storage {
    public DefaultStorage(TabSync plugin) {     
        plugin.getLogger().info("Invalid storage.type provided.");
    }
    public boolean isReady() {
        return false;
    }
}
