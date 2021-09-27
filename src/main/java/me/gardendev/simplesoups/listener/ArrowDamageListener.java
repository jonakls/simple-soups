package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ArrowDamageListener implements Listener {

    private FileManager config;
    private FileManager lang;

    public ArrowDamageListener(PluginCore pluginCore) {
        this.config = pluginCore.getFilesLoader().getConfig();
        this.config = pluginCore.getFilesLoader().getLang();
    }

    @EventHandler
    private void arrowNotify(EntityDamageByEntityEvent event) {
        if (!config.getBoolean("arrow-shot-message")) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager().getType().equals(EntityType.ARROW))) return;

        Player player = (Player) event.getEntity();
        Projectile projectile = (Projectile) event.getDamager();
        ProjectileSource source = projectile.getShooter();

        if(!(source instanceof Player)) return;
        Player damager = (Player) source;

        Bukkit.broadcastMessage("1");
        Bukkit.broadcastMessage("Cause" + event.getCause());
        Bukkit.broadcastMessage("Cause name" + event.getCause().name());
        Bukkit.broadcastMessage("2");
        damager.sendMessage(
                lang.getString("messages.arrow-message")
                        .replace("%player%", player.getName())
                        .replace("%value%", String.valueOf(player.getHealth()))
        );
        Bukkit.broadcastMessage("3");
    }
}
