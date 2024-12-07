package dev.qelli.minecraft.xserversync.utils;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;

public class TabAPIUtils {
    public static String getOriginalName(TabPlayer player) {
        String prefix = TabAPI.getInstance().getTabListFormatManager().getOriginalPrefix(player);
        String suffix = TabAPI.getInstance().getTabListFormatManager().getOriginalSuffix(player);
        return prefix + "%player_displayname%" + suffix;
    }
}
