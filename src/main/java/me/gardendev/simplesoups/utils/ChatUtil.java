package me.gardendev.simplesoups.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

    public static String apply(String path) {
        return ChatColor.translateAlternateColorCodes('&', path);
    }

    public static void commandText(CommandSender sender, String...strings) {
        for(String string : strings) {
            sender.sendMessage(apply(string));
        }
    }

    public static String remove(String path) {
        return ChatColor.stripColor(path);
    }

}
