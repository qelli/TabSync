package dev.qelli.minecraft.xserversync.managers;

import java.util.List;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;
import dev.qelli.minecraft.xserversync.utils.ProtocolLibUtil;

public class TabListManager {

    private XServerSync plugin;

    public TabListManager(XServerSync plugin) {
        this.plugin = plugin;
    }

    public void cleanList(List<PlayerModel> allPlayers) {
        ProtocolLibUtil.sendPacketToAll(
            ProtocolLibUtil.createTabListRemovePacket(allPlayers)
        );
    }

    public void addToList(List<PlayerModel> players) {
        ProtocolLibUtil.sendPacketToAll(
            ProtocolLibUtil.createTabListAddPacket(players)
        );
    }

}
