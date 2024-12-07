package dev.qelli.minecraft.xserversync.placeholders;

import org.bukkit.OfflinePlayer;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.utils.Constants;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    XServerSync plugin;

    public PlaceholderAPIExpansion(XServerSync plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "xserversync";
    }

    @Override
    public String getAuthor() {
        return "blithe_kitsune";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        switch(params) {
            case Constants.Placeholders.InstanceName:
                return plugin.getInstanceManager().getInstanceName();
            case Constants.Placeholders.GlobalPlayers:
                return String.valueOf(plugin.getApi().getGlobalPlayersTotal());
            case Constants.Placeholders.InstancePlayers:
                return String.valueOf(plugin.getApi().getInstancePlayersTotal());
        }

        return null;
    }

}
