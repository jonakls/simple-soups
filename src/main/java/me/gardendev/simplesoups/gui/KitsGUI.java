package me.gardendev.simplesoups.gui;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.builders.ItemBuilder;
import me.gardendev.simplesoups.manager.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitsGUI {

    private final FileManager kits;
    private final FileManager guis;

    public KitsGUI(PluginCore pluginCore) {
        this.kits = pluginCore.getFilesLoader().getKits();
        this.guis = pluginCore.getFilesLoader().getGui();
    }

    public Inventory kits() {
        Inventory inventory = Bukkit.createInventory(
                null,
                guis.getInt("kits.size"),
                guis.getString("kits.title")
        );

        for (String path : kits.getConfigurationSection("kits").getKeys(false)) {

            ItemBuilder builder = new ItemBuilder(
                    Material.valueOf(kits.getString("kits." + path + ".icon.material")),
                    1,
                    kits.getString("kits." + path + ".icon.display"),
                    kits.getStringList("kits." + path + ".icon.lore")
            );

            inventory.setItem(
                    kits.getInt("kits." + path + ".icon.slot-menu"),
                    builder.getItem()
            );

        }

        return inventory;
    }

    public Inventory refill() {
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

}
