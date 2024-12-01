package dev.qelli.minecraft.xserversync.utils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;

import dev.qelli.minecraft.xserversync.messenger.models.PlayerModel;

import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedRemoteChatSessionData;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;

public class ProtocolLibUtil {

    public static PacketContainer createTabListAddPacket(List<PlayerModel> players) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        // TODO: Validate if all these properties are needed at once
        EnumSet<EnumWrappers.PlayerInfoAction> actions = EnumSet.of(
                EnumWrappers.PlayerInfoAction.ADD_PLAYER,
                EnumWrappers.PlayerInfoAction.UPDATE_LATENCY,
                EnumWrappers.PlayerInfoAction.UPDATE_LISTED);
        packet.getPlayerInfoActions().write(0, actions);

        List<PlayerInfoData> data = new ArrayList<>();
        for (PlayerModel player : players) {
            WrappedGameProfile gameProfile = new WrappedGameProfile(player.getUuid(), player.getName());
            gameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", player.getSkin(), UUID.randomUUID().toString()));
            data.add(new PlayerInfoData(
                    player.getUuid(),
                    20,
                    true,
                    NativeGameMode.SURVIVAL,
                    gameProfile,
                    null,
                    (WrappedRemoteChatSessionData) null));
            
        }

        packet.getPlayerInfoDataLists().write(1, data);
        return packet;
    }

    // TODO: Rework info_action -- not working
    public static PacketContainer createTabListUpdatePacket(List<PlayerModel> players) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        EnumSet<EnumWrappers.PlayerInfoAction> actions = EnumSet.of(
            EnumWrappers.PlayerInfoAction.UPDATE_LATENCY,
            EnumWrappers.PlayerInfoAction.UPDATE_LISTED
        );
        packet.getPlayerInfoActions().write(0, actions);

        List<PlayerInfoData> data = new ArrayList<>();
        for (PlayerModel player : players) {
            WrappedGameProfile gameProfile = new WrappedGameProfile(player.getUuid(), player.getName());
            gameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", player.getSkin(), UUID.randomUUID().toString()));
            data.add(new PlayerInfoData(
                    player.getUuid(),
                    20,
                    true,
                    NativeGameMode.SURVIVAL,
                    gameProfile,
                    null,
                    (WrappedRemoteChatSessionData) null));
            
        }

        packet.getPlayerInfoDataLists().write(1, data);
        return packet;
    }

    // public static PacketContainer createTabListAddPacket(UUID uuid, String name, String skin) {
    //     return createTabListAddPacket(Collections.singletonList(new PlayerModel(uuid, name, skin)));
    // }

    public static void sendPacketToPlayer(Player player, PacketContainer packet) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacketToAll(PacketContainer packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendPacketToPlayer(player, packet);
        }
    }

}
