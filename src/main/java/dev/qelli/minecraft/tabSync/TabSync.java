package dev.qelli.minecraft.tabSync;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.tabSync.listeners.PlayerEventListener;
import dev.qelli.minecraft.tabSync.managers.ChatManager;
import dev.qelli.minecraft.tabSync.managers.InstanceManager;
import dev.qelli.minecraft.tabSync.managers.TabListManager;

public final class TabSync extends JavaPlugin {

    private TabListManager tabListManager;
    private ChatManager chatManager;
    private InstanceManager instanceManager;

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        if(!Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            getLogger().severe("ProtocolLib is required for this plugin to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instanceManager = new InstanceManager(this);
        instanceManager.init();
        if(!instanceManager.isReady()) {
            getLogger().severe("InstanceManager failed to initialize.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        tabListManager = new TabListManager(this);
        chatManager = new ChatManager(this);

        getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);

        // ONLY FOR DEV PURPOSES
        getCommand("log").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if(args.length > 0 && args[0].equals("load")) {
                    getLogger().info("Sending sync message...");
                    instanceManager.load();
                }
                getInstanceManager().log();
                return false;
            }
        });
    }

    @Override
    public void onDisable() {
        instanceManager.stop();
    }

    public InstanceManager getInstanceManager() {
        return instanceManager;
    }

    public TabListManager getTabListManager() {
        return tabListManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public boolean isDebug() {
        return getConfig().getBoolean("debug");
    }

}
