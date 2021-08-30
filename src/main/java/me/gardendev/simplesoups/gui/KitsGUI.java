package me.gardendev.simplesoups.gui;

import me.gardendev.simplesoups.builders.ItemBuilder;
import me.gardendev.simplesoups.loader.FilesLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitsGUI {

    private final FilesLoader files;

    public KitsGUI(FilesLoader files) {
        this.files = files;
    }

    public Inventory kits() {
        Inventory inventory = Bukkit.createInventory(
                null,
                files.getGui().getInt("kits.size"),
                files.getGui().getString("kits.title")
        );

        for (String path : files.getKits().getConfigurationSection("kits").getKeys(false)) {

            ItemBuilder builder = new ItemBuilder(
                    Material.valueOf(files.getKits().getString("kits." + path + ".icon.material")),
                    1,
                    files.getKits().getString("kits." + path + ".icon.display"),
                    files.getKits().getStringList("kits." + path + ".icon.lore")
            );

            inventory.setItem(
                    files.getKits().getInt("kits." + path + ".icon.slot-menu"),
                    builder.getItem()
            );

        }

        return inventory;
    }

    public Inventory refill() {
        Inventory inventory = Bukkit.createInventory(
                null,
                36,
                files.getGui().getString("refill.title")
        );

        for(int i = 0; i < inventory.getSize() ; i++) {

            inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));

        }
        return inventory;
    }

}
