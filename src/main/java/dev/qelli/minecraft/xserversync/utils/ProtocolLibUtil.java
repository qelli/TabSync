package dev.qelli.minecraft.xserversync.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.Converters;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;

import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;

import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedRemoteChatSessionData;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;

public class ProtocolLibUtil {

    public static PacketContainer createTabListAddPacket(List<PlayerModel> players) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        EnumSet<EnumWrappers.PlayerInfoAction> actions = EnumSet.of(
                EnumWrappers.PlayerInfoAction.ADD_PLAYER,
                EnumWrappers.PlayerInfoAction.UPDATE_LATENCY,
                EnumWrappers.PlayerInfoAction.UPDATE_LISTED,
                EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME
                // EnumWrappers.PlayerInfoAction.UPDATE_LIST_ORDER // AVAILABLE UNTIL ProtocolLib@5.4.0
            );
        packet.getPlayerInfoActions().write(0, actions);

        List<PlayerInfoData> data = new ArrayList<>();
        for (PlayerModel player : players) {
            WrappedGameProfile gameProfile = new WrappedGameProfile(player.getUuid(), player.getName());
            gameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", player.getSkin(), UUID.randomUUID().toString()));
            WrappedChatComponent displayName = WrappedChatComponent.fromText(player.getDisplayName());
            // TODO: Might need to be readjusted once the official 1.21.3 support for ProtocolLib is released (temptative 5.4.0)
            data.add(new PlayerInfoData(
                    player.getUuid(),
                    20,
                    true,
                    NativeGameMode.SURVIVAL,
                    gameProfile,
                    displayName,
                    (WrappedRemoteChatSessionData) null));
        }
        packet.getPlayerInfoDataLists().write(1, data);
        return packet;
    }

    public static PacketContainer createTabListRemovePacket(List<PlayerModel> players) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO_REMOVE);

        List<UUID> uuids = players.stream().map(PlayerModel::getUuid).collect(Collectors.toList());
        packet.getLists(Converters.passthrough(UUID.class)).write(0, uuids);

        return packet;
    }

    public static void sendPacketToPlayer(Player player, PacketContainer packet) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacketToAll(PacketContainer packet) {
        try {
            ProtocolLibrary.getProtocolManager().broadcastServerPacket(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
