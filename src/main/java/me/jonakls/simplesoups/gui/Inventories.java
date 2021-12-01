package me.jonakls.simplesoups.gui;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.storage.PlayerData;
import me.jonakls.simplesoups.storage.cache.DataCache;
import me.jonakls.simplesoups.utils.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {

    private final FileManager kits;
    private final FileManager guis;
    private final FileManager config;
    private final PlayerData playerData;

    public Inventories(PluginCore pluginCore) {
        this.kits = pluginCore.getFilesLoader().getKits();
        this.guis = pluginCore.getFilesLoader().getGui();
        this.config = pluginCore.getFilesLoader().getConfig();
        this.playerData = pluginCore.getPlayerData();
    }

    public Inventory openKits(Player player) {
        DataCache cache = playerData.getPlayerData(player);

        Inventory inventory = Bukkit.createInventory(
                null,
                guis.getInt("kits.size"),
                guis.getString("kits.title")
        );

        for (String path : kits.getConfigurationSection("kits").getKeys(false)) {

            if (!cache.getKits().contains(path) && player.hasPermission("simplesoups.kit." + path)) {
                inventory.setItem(
                        kits.getInt("kits." + path + ".icon.slot-menu"),
                        this.lockKit()
                );
                continue;
            }

            ItemFactory factory = new ItemFactory(
                    kits.getMaterial("kits." + path + ".icon.material"),
                    1,
                    kits.getString("kits." + path + ".icon.display"),
                    kits.getStringList("kits." + path + ".icon.lore")
            );

            inventory.setItem(
                    kits.getInt("kits." + path + ".icon.slot-menu"),
                    factory.getItem()
            );
        }
        return inventory;
    }

    public Inventory openRefill() {
        Inventory inventory = Bukkit.createInventory(
                null,
                36,
                guis.getString("refill.title")
        );

        for(int i = 0; i < inventory.getSize() ; i++) {

            inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));

        }
        return inventory;
    }

    private ItemStack lockKit() {
        ItemFactory factory = new ItemFactory(
                config.getMaterial("items.locked-kit.material"),
                1,
                config.getString("items.locked-kit.display"),
                config.getStringList("items.locked-kit.lore")
        );
        return factory.getItem();
    }

}
