package dev.qelli.minecraft.xserversync;

import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.xserversync.api.XServerSyncAPI;
import dev.qelli.minecraft.xserversync.commands.XServerSyncCommand;
import dev.qelli.minecraft.xserversync.listeners.DefaultPlayerEventListener;
import dev.qelli.minecraft.xserversync.listeners.TabPluginEventListener;
import dev.qelli.minecraft.xserversync.managers.ChatManager;
import dev.qelli.minecraft.xserversync.managers.InstanceManager;
import dev.qelli.minecraft.xserversync.managers.TabListManager;
import dev.qelli.minecraft.xserversync.placeholders.PlaceholderAPIExpansion;

public final class XServerSync extends JavaPlugin {

    static XServerSync instance;

    private XServerSyncAPI api;
    private TabListManager tabListManager;
    private ChatManager chatManager;
    private InstanceManager instanceManager;

    @Override
    public void onEnable() {

        instance = this;

        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
            saveDefaultConfig();
        }

        // ProtocolLib is a direct dependency.
        if(!getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
            disablePlugin("ProtocolLib is required for this plugin to work.");
            return;
        }

        // If the PlaceholderAPI extension is enabled but the plugin is not found, this wont work
        if (isPlaceholderExpansionEnabled() && getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIExpansion(this).register();
            getLogger().info("PlaceholderAPI expansion was registered.");
        } else {
            disablePlugin("PlaceholderAPI expansion was expected to be enabled but PlaceholderAPI was not found.");
            return;
        }

        initListeners();

        instanceManager = new InstanceManager(this);
        instanceManager.init();
        if(!instanceManager.isReady()) {
            getLogger().severe("InstanceManager failed to initialize.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        tabListManager = new TabListManager(this);
        chatManager = new ChatManager(this);

        getServer().getPluginManager().registerEvents(new DefaultPlayerEventListener(this), this);

        // Not exactly a load but more like a sync
        getServer().getScheduler().runTask(this, () -> {
            instanceManager.load();
        });

        new XServerSyncCommand(this);

        api = new XServerSyncAPI(this);

    }

    @Override
    public void onDisable() {
        instanceManager.stop();
    }

    public static XServerSync getInstance() {
        return instance;
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

    public XServerSyncAPI getApi() {
        return api;
    }

    public boolean isDebug() {
        return getConfig().getBoolean("debug");
    }

    public boolean isPlaceholderExpansionEnabled() {
        return getConfig().getBoolean("placeholderapi");
    }

    public String getPermissionsProvider() {
        return getConfig().getString("permissions.provider");
    }

    public String getTabListProvider() {
        return getConfig().getString("tablist.provider");
    }

    private void disablePlugin(String reason) {
        getLogger().severe("Plugin was disabled. " + reason);
        getServer().getPluginManager().disablePlugin(this);
    }

    private void initListeners() {
        switch (getTabListProvider()) {
            case "TAB_PLUGIN":
                if (!getServer().getPluginManager().isPluginEnabled("TAB")) {
                    disablePlugin("TabList provider was set to 'TAB_PLUGIN' but the plugin was not found.");
                    return;
                }
                new TabPluginEventListener(this);
                break;
            default:
                new DefaultPlayerEventListener(this);
                break;
        }
    }

}
