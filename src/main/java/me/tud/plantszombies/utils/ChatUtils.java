package me.tud.plantszombies.utils;

import org.bukkit.ChatColor;

public final class ChatUtils {

    private ChatUtils() {
        throw new UnsupportedOperationException();
    }

    public static String colored(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
