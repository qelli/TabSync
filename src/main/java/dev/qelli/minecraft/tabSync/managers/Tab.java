package dev.qelli.minecraft.tabSync.managers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class Tab {

    public void addFakePlayer(String name) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        List<PlayerInfoData> fakePlayers = new ArrayList<>();
        fakePlayers.add(new PlayerInfoData(
            new WrappedGameProfile(UUID.randomUUID(), name),
            0,
            EnumWrappers.NativeGameMode.SURVIVAL,
            WrappedChatComponent.fromText(name)
        ));
        packet.getPlayerInfoDataLists().write(0, fakePlayers);
        for(Player player : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void addFakePlayer() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
    
            List<PlayerInfoData> fakePlayers = new ArrayList<>();
            fakePlayers.add(new PlayerInfoData(
                new WrappedGameProfile(UUID.randomUUID(), "FakePlayer1"), // Unique UUID and name
                0, // Ping (0 for fake)
                EnumWrappers.NativeGameMode.SURVIVAL, 
                WrappedChatComponent.fromText("FakePlayer1") // Display name
            ));
    
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
