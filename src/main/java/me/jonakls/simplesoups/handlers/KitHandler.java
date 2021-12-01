package me.jonakls.simplesoups.handlers;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.enums.GameStatus;
import me.jonakls.simplesoups.loader.FilesLoader;
import me.jonakls.simplesoups.utils.ItemFactory;
import me.jonakls.simplesoups.utils.TitleFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class KitHandler {

    private final PluginCore pluginCore;

    public KitHandler(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    public void setPlayerKit(Player player, String kitName) {
        FilesLoader file = pluginCore.getFilesLoader();
        String prefix = file.getLang().getString("prefix");
        if (!player.hasMetadata("status")) return;
        for (MetadataValue meta : player.getMetadata("status")) {

            if (!meta.value().equals(GameStatus.SPAWN)) {
                player.sendMessage(prefix + file.getLang().getString("error.already-kit"));
                return;
            }

            PlayerInventory inventory = player.getInventory();

            inventory.clear();
            inventory.setHelmet(null);
            inventory.setChestplate(null);
            inventory.setLeggings(null);
            inventory.setBoots(null);

            TitleFactory builder = new TitleFactory(
                    file.getLang().getString("titles.select-kit.title").replace("%kit%", kitName),
                    file.getLang().getString("titles.select-kit.sub-title").replace("%kit%", kitName)
            );

            builder.setTime(
                    file.getLang().getInt("titles.select-kit.fade-in"),
                    file.getLang().getInt("titles.select-kit.stay"),
                    file.getLang().getInt("titles.select-kit.fade-out")
            );

            builder.send(player);

            player.sendMessage(prefix + file.getLang().getString("messages.select-kit").replace("%kit%", kitName));

            player.setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.IN_GAME));

            for (String path : file.getKits().getConfigurationSection("kits").getKeys(false)) {

                if (path.toLowerCase().equals(kitName)) {

                    ItemFactory helmet = new ItemFactory(file.getKits().getString("kits." + path + ".armor.head").split(";"));
                    ItemFactory body = new ItemFactory(file.getKits().getString("kits." + path + ".armor.body").split(";"));
                    ItemFactory leggins = new ItemFactory(file.getKits().getString("kits." + path + ".armor.leggins").split(";"));
                    ItemFactory boats = new ItemFactory(file.getKits().getString("kits." + path + ".armor.boats").split(";"));

                    inventory.setHelmet(helmet.getItem());

                    inventory.setChestplate(body.getItem());

                    inventory.setLeggings(leggins.getItem());

                    inventory.setBoots(boats.getItem());

                    List<String> items = file.getKits().getStringList("kits." + path + ".items");

                    for (int i = 0 ; i < items.size() ; i++) {

                        String[] strings = items.get(i).split(";");

                        ItemFactory itemBuilder = new ItemFactory(strings);

                        inventory.setItem(i, itemBuilder.getItem());

                    }
                }

            }
        }
    }



}
