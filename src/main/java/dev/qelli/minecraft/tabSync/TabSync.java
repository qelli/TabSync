package dev.qelli.minecraft.tabSync;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.qelli.minecraft.tabSync.listeners.PlayerEventListener;
import dev.qelli.minecraft.tabSync.managers.ChatManager;
import dev.qelli.minecraft.tabSync.managers.TabListManager;
import dev.qelli.minecraft.tabSync.messenger.Messenger;
import dev.qelli.minecraft.tabSync.utils.Constants;

public final class TabSync extends JavaPlugin {

    private Messenger messenger;
    private TabListManager tabListManager;
    private ChatManager chatManager;

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

        messenger = new Messenger(this);
        messenger.start();
        if(!messenger.isEnabled()) {
            getLogger().severe("Failed to initialize messenger, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // TODO: Consider moving somewhere else
        tabListManager = new TabListManager(this);
        chatManager = new ChatManager(this);

        /*
         * Channel to keep TabList in sync
         */
        messenger.subscribeToTabListUpdates((channel, messages) -> {
            getLogger().info("Received: " + messages[1]);
            switch(messages[1]) {
                case Constants.TAB_ACTION_JOIN:
                    tabListManager.addPlayer(UUID.fromString(messages[2]), messages[3]);
                    break;
                case Constants.TAB_ACTION_QUIT:
                    tabListManager.removePlayer(UUID.fromString(messages[2]));
                    break;
            }
        });

        /*
         * Channel to keep Chat in sync
         * 
         * Receives:
         */
        // messenger.subscribe(Constants.CHAT_CHANNEL_NAME).consume((channel, message) -> {
        //     getLogger().info("Received message: " + message);
        // });

        getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);

    }

    @Override
    public void onDisable() {
        messenger.clear();
        messenger.close();
    }

    public TabListManager getTabListManager() {
        return tabListManager;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public boolean isDebug() {
        return getConfig().getBoolean("debug");
    }

}
