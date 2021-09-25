package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.PluginCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PluginCore pluginCore;

    public PlayerQuitListener(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.getPlayer().setLevel(0);
    }

    @EventHandler
    public void saveOnQuit(PlayerQuitEvent event) {
        pluginCore.getPlayerData().savePlayerData(event.getPlayer());
    }

    @EventHandler
    public void deleteScoreboard(PlayerQuitEvent event) {
        pluginCore.gameScoreboard().delete(event.getPlayer());
    }
}