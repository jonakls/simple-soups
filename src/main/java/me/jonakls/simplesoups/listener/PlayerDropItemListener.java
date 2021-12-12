package me.jonakls.simplesoups.listener;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    private final FileManager config;

    public PlayerDropItemListener(PluginCore pluginCore) {
        this.config = pluginCore.getFilesLoader().getConfig();
    }

    @EventHandler
    private void onItemBowl(PlayerDropItemEvent event) {
        if (event.getItemDrop() == null) return;
        if (!event.getItemDrop().getItemStack().getType().equals(Material.BOWL)) {
            event.setCancelled(config.getBoolean("deny-drops"));
            return;
        }
        event.setCancelled(false);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 10, 5);
    }
}
