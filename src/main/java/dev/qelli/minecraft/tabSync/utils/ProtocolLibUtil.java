package dev.qelli.minecraft.tabSync.utils;

import java.util.Collections;
import java.util.EnumSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedRemoteChatSessionData;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;

public class ProtocolLibUtil {
    
    public static PacketContainer createTabListAddPacket(UUID uuid, String name, String skinValue) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        EnumSet<EnumWrappers.PlayerInfoAction> actions = EnumSet.of(
                EnumWrappers.PlayerInfoAction.ADD_PLAYER,
                EnumWrappers.PlayerInfoAction.UPDATE_LATENCY,
                EnumWrappers.PlayerInfoAction.UPDATE_LISTED);
        packet.getPlayerInfoActions().write(0, actions);

        // Set skin
        WrappedGameProfile gameProfile = new WrappedGameProfile(uuid, name);
        gameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", skinValue, UUID.randomUUID().toString()));

        // Create player info
        PlayerInfoData data = new PlayerInfoData(
                uuid,
                20,
                true,
                NativeGameMode.SURVIVAL,
                gameProfile,
                null,
                (WrappedRemoteChatSessionData) null);
        packet.getPlayerInfoDataLists().write(1, Collections.singletonList(data));
        return packet;
    }

    public static void sendPacketToAll(PacketContainer packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
