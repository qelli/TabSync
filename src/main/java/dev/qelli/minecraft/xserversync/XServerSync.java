package dev.qelli.minecraft.xserversync;

import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.xserversync.commands.XServerSyncCommand;
import dev.qelli.minecraft.xserversync.listeners.PlayerEventListener;
import dev.qelli.minecraft.xserversync.managers.ChatManager;
import dev.qelli.minecraft.xserversync.managers.InstanceManager;
import dev.qelli.minecraft.xserversync.managers.TabListManager;

public final class XServerSync extends JavaPlugin {

    private TabListManager tabListManager;
    private ChatManager chatManager;
    private InstanceManager instanceManager;

    @Override
    public void onEnable() {

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        if(!getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
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

        // Not exactly a load but more like a sync
        getServer().getScheduler().runTask(this, () -> {
            instanceManager.load();
        });

        new XServerSyncCommand(this);

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
