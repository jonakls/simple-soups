package me.jonakls.simplesoups.handlers;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class DeathMessagesHandler {

    private final FileManager lang;

    public DeathMessagesHandler(PluginCore pluginCore) {
        this.lang = pluginCore.getFilesLoader().getLang();
    }

    public void objectsDamage(Player player) {
        String prefix = lang.getString("prefix");

        switch (player.getLastDamageCause().getCause()) {
            case FALL:
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-fall-damage")
                        .replace("%player%", player.getName()));
                break;
            case LAVA:
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-lava")
                        .replace("%player%", player.getName()));
                break;
            case FIRE:
            case FIRE_TICK:
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-fire")
                        .replace("%player%", player.getName()));
                break;
            case ENTITY_EXPLOSION:
            case BLOCK_EXPLOSION:
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-explosion")
                        .replace("%player%", player.getName()));
                break;
            default:
                Bukkit.broadcastMessage(prefix + lang.getString("kill-messages.death-by-unknown")
                        .replace("%player%", player.getName()));
                break;
        }
    }

    public void playersDamage(Player player, Player killer) {
        String prefix = lang.getString("prefix");

        Random random = new Random();
        List<String> killsMessages = lang.getStringList("kill-messages.with-players");
        Bukkit.broadcastMessage(prefix + killsMessages.get(random.nextInt(killsMessages.size()))
                .replace("%player%", player.getName())
                .replace("%killer%", killer.getName()));
    }

}
