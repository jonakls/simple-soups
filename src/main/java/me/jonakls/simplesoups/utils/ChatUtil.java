package me.jonakls.simplesoups.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

    public static String toLegacyColors(String path) {
        return ChatColor.translateAlternateColorCodes('&', path);
    }

    public static void sendArrayMessages(CommandSender sender, String...strings) {
        for(String string : strings) {
            sender.sendMessage(toLegacyColors(string));
        }
    }

    public static String remove(String path) {
        return ChatColor.stripColor(path);
    }

}
