package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.loader.FilesLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChangeListener implements Listener {

    private final FilesLoader files;

    public FoodChangeListener(FilesLoader files) {
        this.files = files;
    }

    @EventHandler
    public void foodLevel(FoodLevelChangeEvent event) {
        event.setCancelled(files.getConfig().getBoolean("deny-saturation"));
    }
}
