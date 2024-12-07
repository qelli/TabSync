package dev.qelli.minecraft.xserversync.api;

import dev.qelli.minecraft.xserversync.XServerSync;

public class XServerSyncAPI {
    
    XServerSync plugin;

    public XServerSyncAPI(XServerSync plugin) {
        this.plugin = plugin;
    }

    public String getInstanceName() {
        return plugin.getInstanceManager().getInstanceName();
    }

    public Integer getGlobalPlayersTotal() {
        return plugin.getInstanceManager().getAllPlayers().size();
    }

    public Integer getInstancePlayersTotal() {
        return plugin.getInstanceManager().getInstancePlayers().size();
    }


}
