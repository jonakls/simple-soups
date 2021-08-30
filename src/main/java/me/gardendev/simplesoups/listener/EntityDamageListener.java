package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.loader.FilesLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private final FilesLoader files;

    public EntityDamageListener(FilesLoader files) {
        this.files = files;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL) ||
                event.getCause().equals(EntityDamageEvent.DamageCause.FALLING_BLOCK)) return;

        event.setCancelled(files.getConfig().getBoolean("fall-damage"));

    }


}
