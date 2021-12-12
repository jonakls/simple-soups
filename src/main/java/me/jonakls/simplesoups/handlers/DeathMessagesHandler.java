package me.jonakls.simplesoups.handlers;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DeathMessagesHandler {

    private final FileManager lang;
    private final Map<EntityDamageEvent.DamageCause, String> deathMessages;

    public DeathMessagesHandler(PluginCore pluginCore) {
        this.lang = pluginCore.getFilesLoader().getLang();
        this.deathMessages = new HashMap<>();
        setupDeathMessages();
    }

    private void setupDeathMessages() {
        String prefix = lang.getString("prefix");
        deathMessages.put(EntityDamageEvent.DamageCause.FALL, prefix + lang.getString("kill-messages.death-by-fall-damage"));
        deathMessages.put(EntityDamageEvent.DamageCause.LAVA, prefix + lang.getString("kill-messages.death-by-lava"));
        deathMessages.put(EntityDamageEvent.DamageCause.FIRE, prefix + lang.getString("kill-messages.death-by-fire"));
        deathMessages.put(EntityDamageEvent.DamageCause.FIRE_TICK, prefix + lang.getString("kill-messages.death-by-fire"));
        deathMessages.put(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, prefix + lang.getString("kill-messages.death-by-explosion"));
        deathMessages.put(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, prefix + lang.getString("kill-messages.death-by-explosion"));
    }

    public void broadcastDeathMessage(Player player) {
        String prefix = lang.getString("prefix");

        if (deathMessages.containsKey(player.getLastDamageCause().getCause())) {
            Bukkit.broadcastMessage(deathMessages.get(player.getLastDamageCause().getCause()));
            return;
        }

        Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-unknown")
                .replace("%player%", player.getName()));
    }

    public void broadcastDeathMessage(Player player, Player killer) {
        String prefix = lang.getString("prefix");

        Random random = new Random();
        List<String> killsMessages = lang.getStringList("kill-messages.with-players");
        Bukkit.broadcastMessage(prefix + killsMessages.get(random.nextInt(killsMessages.size()))
                .replace("%player%", player.getName())
                .replace("%killer%", killer.getName()));
    }

}
