package me.jonakls.simplesoups.listener;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private final FileManager config;

    public SignChangeListener(PluginCore pluginCore) {
        this.config = pluginCore.getFilesLoader().getConfig();
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getBlock() == null) return;
        if (!event.getBlock().getType().equals(Material.WALL_SIGN)) return;

        String[] lines = config.getString("signs.soups").split(";");

        if (event.getLine(0).equalsIgnoreCase("[soup]")) {

            for (int i = 0; i < lines.length; i++) {

                event.setLine(
                        i,
                        ChatUtil.toLegacyColors(lines[i])
                );

            }
            return;
        }
        event.setCancelled(true);
    }


}
