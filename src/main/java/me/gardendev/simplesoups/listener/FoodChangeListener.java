package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.loader.FilesLoader;
import me.gardendev.simplesoups.manager.FileManager;
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
