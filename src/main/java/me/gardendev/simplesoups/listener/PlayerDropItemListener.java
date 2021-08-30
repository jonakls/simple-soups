package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.loader.FilesLoader;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    private final FilesLoader files;

    public PlayerDropItemListener(FilesLoader files){
        this.files = files;
    }

    @EventHandler
    private void onItemBowl(PlayerDropItemEvent event) {
        if (event.getItemDrop() == null) return;
        if (!event.getItemDrop().getItemStack().getType().equals(Material.BOWL)){
            event.setCancelled(files.getConfig().getBoolean("deny-drops"));
            return;
        }
        event.setCancelled(false);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 10, 5);
    }
}
