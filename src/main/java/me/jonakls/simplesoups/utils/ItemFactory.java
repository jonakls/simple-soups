package me.jonakls.simplesoups.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemFactory {

    private final ItemStack item;

    public ItemFactory(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemFactory(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemFactory(Material material, int amount, String itemName) {
        this.item = new ItemStack(material, amount);
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(itemName);
        this.item.setItemMeta(meta);
    }

    public ItemFactory(Material material, int amount, String itemName, List<String> loreItem) {
        this.item = new ItemStack(material, amount);
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(loreItem);
        meta.setDisplayName(itemName);
        this.item.setItemMeta(meta);
    }

    public ItemFactory(String[] strings) {
        switch (strings.length) {
            case 1:
                this.item = new ItemStack(Material.valueOf(strings[0]));
                break;
            case 2:
                this.item = new ItemStack(Material.valueOf(strings[0]), Integer.parseInt(strings[1]));
                break;
            case 3:
                this.item = new ItemStack(Material.valueOf(strings[0]), Integer.parseInt(strings[1]));
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(strings[2]);
                this.item.setItemMeta(meta);
                break;
            default:
                this.item = new ItemStack(Material.valueOf(strings[0]), Integer.parseInt(strings[1]));
                ItemMeta meta2 = item.getItemMeta();
                meta2.setDisplayName(strings[2]);
                for (int i = 4; i <= strings.length; i++) {
                    String[] enchant = strings[i - 1].split(":");
                    meta2.addEnchant(
                            Enchantment.getByName(enchant[0]),
                            Integer.parseInt(enchant[1]),
                            true
                    );
                }
                this.item.setItemMeta(meta2);
                break;
        }
    }

    public ItemStack getItem() {
        return item;
    }
}
