package me.gardendev.simplesoups.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Colorized {

    public static String apply(String path) {
        return ChatColor.translateAlternateColorCodes('&', path);
    }

    public static void commandText(CommandSender sender, String...strings) {
        for(String string : strings) {
            sender.sendMessage(apply(string));
        }
    }

    public static String[] applyArray(String...strings) {
        String[] colorized = new String[strings.length + 1];
        for(int i = 0 ; i < strings.length ; i++) {
            colorized[i] = ChatColor.translateAlternateColorCodes('&', strings[i]);
        }
        return colorized;
    }

    public static String remove(String path) {
        return ChatColor.stripColor(path);
    }

}
