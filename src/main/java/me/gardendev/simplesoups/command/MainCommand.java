package me.gardendev.simplesoups.command;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.manager.FileManager;
import me.gardendev.simplesoups.enums.GameStatus;
import me.gardendev.simplesoups.utils.Colorized;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor{

    private final PluginCore pluginCore;

    public MainCommand(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileManager config = pluginCore.getFilesLoader().getConfig();
        FileManager lang = pluginCore.getFilesLoader().getLang();

        String prefix = lang.getString("prefix");

        if (!(sender instanceof Player)) {

            if (!(args.length > 0)) {
                List<String> help = new ArrayList<>();

                help.add(prefix + "&7help commands");
                help.add("&c- /soup help &8| &7Show help commands");
                help.add("&c- /soup reload &8| &7Reload all files of config");
                help.add("&c- /soup kits &8| &7Open gui of kits");

                for (String line : help) {

                    sender.sendMessage(Colorized.apply(line));

                }
                return false;
            }

            if ("reload".equalsIgnoreCase(args[0])) {
                lang.reload();
                config.reload();
                pluginCore.getFilesLoader().getKits().reload();
                pluginCore.getFilesLoader().getGui().reload();
                sender.sendMessage(prefix + lang.getString("messages.reload"));
                return true;
            }
            sender.sendMessage(prefix + lang.getString("error.unknown-command"));
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length > 0)) {
            List<String> help = new ArrayList<>();

            help.add(prefix + "&7help commands");
            help.add("&c- /soup help &8| &7Show help commands");
            help.add("&c- /soup reload &8| &7Reload all files of config");
            help.add("&c- /soup kits &8| &7Open gui of kits");

            for (String line : help) {

                player.sendMessage(Colorized.apply(line));

            }
            return false;
        }

        switch (args[0].toLowerCase()){
            case "save":
                pluginCore.getStorage().insert(
                        pluginCore.getPlayerCache(),
                        player
                );
                break;
            case "getData":

                player.sendMessage("Datos");
                player.sendMessage(player.getName() + ", kills: " + pluginCore.getStorage().getKills(player.getUniqueId().toString()));
                break;
            case "reload":
                lang.reload();
                config.reload();
                pluginCore.getFilesLoader().getKits().reload();
                pluginCore.getFilesLoader().getGui().reload();
                sender.sendMessage(prefix + lang.getString("messages.reload"));
                break;
            case "kits":
                player.openInventory(pluginCore.getKitsGUI().kits());
                break;
            case "stats":

                player.sendMessage(Colorized.apply("&aYour stats &8| &c" + player.getName()));
                player.sendMessage(" ");
                player.sendMessage(Colorized.apply("&cKills: &f" + pluginCore.getPlayerCache().getKills(player)));
                player.sendMessage(Colorized.apply("&cDeaths: &f" + pluginCore.getPlayerCache().getDeaths(player)));
                player.sendMessage(Colorized.apply("&cXp: &f" + pluginCore.getPlayerCache().getXp(player) ));
                break;
            case "editmode":
                if(player.hasMetadata("status")) {

                    for (MetadataValue value : player.getMetadata("status")) {
                        if (!value.value().equals(GameStatus.EDIT_MODE)) {
                            player.setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.EDIT_MODE));
                            player.sendMessage(lang.getString("messages.enabled-edit-mode"));
                            return true;
                        }
                        player.setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.SPAWN));
                        player.sendMessage(lang.getString("messages.disable-edit-mode"));
                    }
                    return true;
                }

                break;
            case "setspawn":
                if (!(config.contains("spawn.world"))) {

                    config.set("spawn.world", player.getWorld().getName());
                    config.set("spawn.x", player.getLocation().getX());
                    config.set("spawn.y", player.getLocation().getY());
                    config.set("spawn.z", player.getLocation().getZ());
                    config.set("spawn.yaw", player.getLocation().getYaw());
                    config.set("spawn.pitch", player.getLocation().getPitch());
                    config.save();

                    player.sendMessage(prefix + lang.getString("messages.set-spawn"));

                    return true;
                }

                config.set("spawn.world", player.getWorld().getName());
                config.set("spawn.x", player.getLocation().getX());
                config.set("spawn.y", player.getLocation().getY());
                config.set("spawn.z", player.getLocation().getZ());
                config.set("spawn.yaw", player.getLocation().getYaw());
                config.set("spawn.pitch", player.getLocation().getPitch());
                config.save();

                player.sendMessage(prefix + lang.getString("messages.change-spawn"));

                break;
            case "spawn":
                if (!(config.contains("spawn.world"))) {
                    player.sendMessage(prefix + lang.getString("error.no-spawn"));
                    return true;
                }

                player.teleport(new Location(
                        Bukkit.getWorld(config.getString("spawn.world")),
                        config.getDouble("spawn.x"),
                        config.getDouble("spawn.y"),
                        config.getDouble("spawn.z"),
                        (float) config.getDouble("spawn.yaw"),
                        (float) config.getDouble("spawn.pirch")
                ));
                break;
            default:
                sender.sendMessage(prefix + lang.getString("error.unknown-command"));
                break;
        }

        return false;
    }
}
