package me.jonakls.simplesoups.listener;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private final FileManager config;

    public EntityDamageListener(PluginCore pluginCore) {
        this.config = pluginCore.getFilesLoader().getConfig();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL) ||
                event.getCause().equals(EntityDamageEvent.DamageCause.FALLING_BLOCK)) return;

        event.setCancelled(config.getBoolean("fall-damage"));

    }


}
