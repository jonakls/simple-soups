package me.gardendev.simplesoups.listener;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.utils.ItemFactory;
import me.gardendev.simplesoups.enums.GameStatus;
import me.gardendev.simplesoups.loader.FilesLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerJoinListener implements Listener {

    private final PluginCore pluginCore;

    public PlayerJoinListener(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void loadPlayerData(PlayerJoinEvent event) {
        pluginCore.getPlugin().getServer().getLogger().info("[SoupPvP]: Load data of " + event.getPlayer().getName());
        pluginCore.getPlayerCache().loadPlayerData(event.getPlayer());
    }

    @EventHandler
    public void loadScoreboard(PlayerJoinEvent event) {
        pluginCore.gameScoreboard().create(event.getPlayer());
    }

    @EventHandler
    public void joinPlayerItems(PlayerJoinEvent event) {
        FilesLoader file = pluginCore.getFilesLoader();
        event.getPlayer().setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.SPAWN));
        if (!file.getConfig().getBoolean("items-on-join")) return;

        if (file.getConfig().getBoolean("clear-on-join")){
            event.getPlayer().getInventory().clear();
            event.getPlayer().getInventory().setHelmet(null);
            event.getPlayer().getInventory().setChestplate(null);
            event.getPlayer().getInventory().setLeggings(null);
            event.getPlayer().getInventory().setBoots(null);

        }

        ItemFactory builder = new ItemFactory(
                Material.valueOf(file.getConfig().getString("items-join.kits.material")),
                1,
                file.getConfig().getString("items-join.kits.display"),
                file.getConfig().getStringList("items-join.kits.lore")
        );

        event.getPlayer().getInventory().setItem(
                file.getConfig().getInt("items-join.kits.slot"),
                builder.getItem()
        );
    }

    @EventHandler
    public void joinPlayerTeleport(PlayerJoinEvent event) {
        FilesLoader file = pluginCore.getFilesLoader();

        if(!file.getConfig().getBoolean("join-teleport")) return;
        if (file.getConfig().contains("spawn.world")) {
            event.getPlayer().teleport(new Location(
                    Bukkit.getWorld(file.getConfig().getString("spawn.world")),
                    file.getConfig().getDouble("spawn.x"),
                    file.getConfig().getDouble("spawn.y"),
                    file.getConfig().getDouble("spawn.z"),
                    (float) file.getConfig().getDouble("spawn.yaw"),
                    (float) file.getConfig().getDouble("spawn.pirch")
            ));
        }
    }




}
