package me.jonakls.simplesoups.listener;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ArrowDamageListener implements Listener {

    private final FileManager config;
    private final FileManager lang;

    public ArrowDamageListener(PluginCore pluginCore) {
        this.config = pluginCore.getFilesLoader().getConfig();
        this.lang = pluginCore.getFilesLoader().getLang();
    }

    @EventHandler
    private void arrowNotify(EntityDamageByEntityEvent event) {
        String prefix = lang.getString("prefix");
        if (!config.getBoolean("arrow-shot-message")) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager().getType().equals(EntityType.ARROW))) return;

        Player player = (Player) event.getEntity();
        Projectile projectile = (Projectile) event.getDamager();
        ProjectileSource source = projectile.getShooter();

        if (!(source instanceof Player)) return;
        Player damager = (Player) source;

        damager.sendMessage(prefix + lang.getString("messages.arrow-message")
                .replace("%player%", player.getName())
                .replace("%value%", String.valueOf(Math.round(player.getHealth())))
        );
    }
}
