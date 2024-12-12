package dev.qelli.minecraft.xserversync.utils;

import com.saicone.mcode.util.text.MStrings;

public class ChatUtils {

    static {
        MStrings.BUNGEE_HEX = true;
    }
    
    public static String withColor(String message) {
        return MStrings.color(message);
    }

}
