package me.jonakls.simplesoups.handlers;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.enums.GameStatus;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.utils.ItemFactory;
import me.jonakls.simplesoups.utils.TitleFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class KitHandler {

    private final PluginCore pluginCore;
    private final FileManager lang;
    private final FileManager kits;

    public KitHandler(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
        this.lang = pluginCore.getFilesLoader().getLang();
        this.kits = pluginCore.getFilesLoader().getKits();
    }

    public void setPlayerKit(Player player, String kitName) {
        String prefix = lang.getString("prefix");
        if (!player.hasMetadata("status")) return;

        MetadataValue meta = player.getMetadata("status").get(0);
        if (!meta.value().equals(GameStatus.SPAWN)) {
            player.sendMessage(prefix + lang.getString("error.already-kit"));
            return;
        }

        PlayerInventory inventory = player.getInventory();

        inventory.clear();
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);

        TitleFactory titleFactory = new TitleFactory(
                lang.getString("titles.select-kit.title").replace("%kit%", kitName),
                lang.getString("titles.select-kit.sub-title").replace("%kit%", kitName)
        );

        titleFactory.setTime(
                lang.getInt("titles.select-kit.fade-in"),
                lang.getInt("titles.select-kit.stay"),
                lang.getInt("titles.select-kit.fade-out")
        );

        titleFactory.send(player);

        player.sendMessage(prefix + lang.getString("messages.select-kit").replace("%kit%", kitName));

        player.setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.IN_GAME));

        for (String path : kits.getConfigurationSection("kits").getKeys(false)) {
            pluginCore.getPlugin().getLogger().info("Test: ${date:YYYY}");

            if (path.toLowerCase().equals(kitName)) {

                ItemFactory helmet = new ItemFactory(kits.getString("kits." + path + ".armor.head").split(";"));
                ItemFactory body = new ItemFactory(kits.getString("kits." + path + ".armor.body").split(";"));
                ItemFactory leggins = new ItemFactory(kits.getString("kits." + path + ".armor.leggins").split(";"));
                ItemFactory boats = new ItemFactory(kits.getString("kits." + path + ".armor.boats").split(";"));

                inventory.setHelmet(helmet.getItem());
                inventory.setChestplate(body.getItem());
                inventory.setLeggings(leggins.getItem());
                inventory.setBoots(boats.getItem());

                List<String> items = kits.getStringList("kits." + path + ".items");

                for (int i = 0; i < items.size(); i++) {
                    String[] strings = items.get(i).split(";");
                    ItemFactory itemFactory = new ItemFactory(strings);
                    inventory.setItem(i, itemFactory.getItem());
                }
            }

        }
    }


}
