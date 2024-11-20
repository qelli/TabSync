package dev.qelli.minecraft.tabSync;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

import dev.qelli.minecraft.tabSync.listeners.PlayerEventListener;
import dev.qelli.minecraft.tabSync.managers.Tab;
import dev.qelli.minecraft.tabSync.storage.Storage;
import dev.qelli.minecraft.tabSync.storage.impl.DefaultStorage;
import dev.qelli.minecraft.tabSync.storage.impl.RedisStorage;
import me.neznamy.tab.api.TabAPI;

public final class TabSync extends JavaPlugin {

    private TabAPI tab;
    private Storage storage;
    private Tab tabManager = new Tab();

    public Tab getTabManager() {
        return tabManager;
    }

    @Override
    public void onEnable() {

        // tabManager.test();

        this.getCommand("fakeplayers").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

                if(args.length == 0 || args.length > 1) {
                    sender.sendMessage("Usage: /fakeplayers <name>");
                    return false;
                }

                String name = args[0];
                sendFuckingPacket(name);
                // Player player = (Player) sender;

                // sender.sendMessage(("Works without protocollib"));
                // return true;

                // try {
                //     final PacketContainer packet = new PacketContainer(PacketType.Play.Server.EXPLOSION);
                //     packet.getDoubles()
                //         .write(0, player.getLocation().getX() + 1)
                //         .write(1, player.getLocation().getY() + 1)
                //         .write(2, player.getLocation().getZ() + 1);
                //     packet.getFloat().write(0, 3.0f);
                //     packet.getBlockPositionCollectionModifier().write(0, new ArrayList<>());
                //     packet.getVectors().write(0, player.getVelocity().add(new Vector(1, 1, 1)));
                //     ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                // } catch (Exception e) {
                //     e.printStackTrace();
                //     return false;
                // }

                return true;


                // try {
                //     ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                // } catch (Exception e) {
                //     e.printStackTrace();
                //     return false;
                // }

                // PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
                // packet.getModifier().writeDefaults();

                // packet.getPlayerInfoActions().write(1, EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER));
                // List<PlayerInfoData> fakePlayers = new ArrayList<>();
                // fakePlayers.add(new PlayerInfoData(
                //         new WrappedGameProfile(UUID.randomUUID(), name),
                //         0,
                //         EnumWrappers.NativeGameMode.SURVIVAL,
                //         WrappedChatComponent.fromText(name)));
                // packet.getPlayerInfoDataLists().write(0, fakePlayers);
                // for(Player player : Bukkit.getOnlinePlayers()) {
                //     try {
                //         ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                //     } catch (Exception e) {
                //         e.printStackTrace();
                //         return false;
                //     }
                // }

                
                // PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                // packet.getModifier().writeDefaults();

                // WrappedGameProfile profile = new WrappedGameProfile(UUID.randomUUID(), name);

                // packet.getPlayerInfoDataLists().write(0, )

                // packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

                // return true;

                // PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                // packet.getPlayerInfoActions().write(0, EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER));
                // List<PlayerInfoData> fakePlayers = new ArrayList<>();
                // fakePlayers.add(new PlayerInfoData(
                //         new WrappedGameProfile(UUID.randomUUID(), name),
                //         0,
                //         EnumWrappers.NativeGameMode.SURVIVAL,
                //         WrappedChatComponent.fromText(name)));
                // packet.getPlayerInfoDataLists().write(0, fakePlayers);
                // for (Player player : Bukkit.getOnlinePlayers()) {
                //     try {
                //         ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                //     } catch (Exception e) {
                //         e.printStackTrace();
                //     }
                // }

                // return true;
            }
        });

        // tab = TabAPI.getInstance();
        // if(tab == null) {
        // getLogger().severe("Failed to get TabAPI instance, disabling plugin.");
        // getServer().getPluginManager().disablePlugin(this);
        // return;
        // }

        // initStorage();
        // if(!storage.isReady()) {
        // getLogger().severe("Failed to initialize storage, disabling plugin.");
        // getServer().getPluginManager().disablePlugin(this);
        // return;
        // }

        // getServer().getPluginManager().registerEvents(new PlayerEventListener(this),
        // this);

    }

    @Override
    public void onDisable() {}

    private void sendFuckingPacket(String name) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoActions().write(0x01, EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER));
        packet.getPlayerInfoActions().write(1, EnumSet.of(EnumWrappers.PlayerInfoAction.ADD_PLAYER));
        // List<PlayerInfoData> fakePlayers = new ArrayList<>();
        // fakePlayers.add(new PlayerInfoData(
        //         new WrappedGameProfile(UUID.randomUUID(), name),
        //         0,
        //         EnumWrappers.NativeGameMode.SURVIVAL,
        //         WrappedChatComponent.fromText(name)));
        // packet.getPlayerInfoDataLists().write(0, fakePlayers);
        // for (Player player : Bukkit.getOnlinePlayers()) {
        //     try {
        //         ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
    }

    private void initStorage() {
        switch (getConfig().getString("storage.type").toLowerCase()) {
            // case "mysql":
            // storage = new MySQLStorage();
            // break;
            case "redis":
                storage = new RedisStorage(this);
                break;
            default:
                storage = new DefaultStorage(this);
        }
    }

    TabAPI getTab() {
        return tab;
    }

    Storage getStorage() {
        return storage;
    }

}
