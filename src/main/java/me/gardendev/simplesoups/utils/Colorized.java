package me.gardendev.simplesoups.utils;

import org.bukkit.ChatColor;

public class Colorized {

    public static String apply(String path) {
        return ChatColor.translateAlternateColorCodes('&', path);
    }

    public static String remove(String path) {
        return ChatColor.stripColor(path);
    }

}
