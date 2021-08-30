package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.PluginCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {

    private final PluginCore pluginCore;

    public PlayerPickupListener(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(pluginCore.getFilesLoader().getConfig().getBoolean("pickup-items"));
    }
}
