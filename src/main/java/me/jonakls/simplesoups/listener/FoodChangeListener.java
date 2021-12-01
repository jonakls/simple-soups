package me.jonakls.simplesoups.listener;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChangeListener implements Listener {

    private final FileManager config;

    public FoodChangeListener(PluginCore pluginCore) {
        this.config = pluginCore.getFilesLoader().getConfig();
    }

    @EventHandler
    public void foodLevel(FoodLevelChangeEvent event) {
        event.setCancelled(config.getBoolean("deny-saturation"));
    }
}
