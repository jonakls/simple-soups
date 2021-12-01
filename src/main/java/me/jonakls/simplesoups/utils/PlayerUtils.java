package me.jonakls.simplesoups.utils;

import org.bukkit.entity.Player;

public class PlayerUtils {

    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setBoots(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setHelmet(null);
    }
}
