package me.jonakls.simplesoups.command;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.enums.GameStatus;
import me.jonakls.simplesoups.gui.Inventories;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.storage.PlayerData;
import me.jonakls.simplesoups.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class MainCommand implements CommandExecutor {

    private final PluginCore pluginCore;
    private final PlayerData playerData;
    private final FileManager config;
    private final FileManager lang;
    private final FileManager guis;
    private final FileManager kits;

    public MainCommand(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
        this.playerData = pluginCore.getPlayerData();
        this.config = pluginCore.getFilesLoader().getConfig();
        this.lang = pluginCore.getFilesLoader().getLang();
        this.kits = pluginCore.getFilesLoader().getKits();
        this.guis = pluginCore.getFilesLoader().getKits();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Inventories inventories = pluginCore.getInventories();
        String prefix = lang.getString("prefix");

        if (!(sender instanceof Player)) {

            if (!(args.length > 0)) {

                ChatUtil.sendArrayMessages(
                        sender,
                        prefix + "&7help commands",
                        "&c- /soup help &8| &7Show help commands",
                        "&c- /soup reload &8| &7Reload all files of config",
                        "&c- /soup kits &8| &7Open gui of kits"
                );
                return false;
            }

            if ("reload".equalsIgnoreCase(args[0])) {
                lang.reload();
                config.reload();
                kits.reload();
                guis.reload();
                sender.sendMessage(prefix + lang.getString("messages.reload"));
                return true;
            }
            sender.sendMessage(prefix + lang.getString("error.unknown-command"));
            return true;
        }

        Player player = (Player) sender;

        if (!(args.length > 0)) {
            ChatUtil.sendArrayMessages(
                    player,
                    prefix + "&7help commands",
                    "&c- /soup help &8| &7Show help commands",
                    "&c- /soup reload &8| &7Reload all files of config",
                    "&c- /soup kits &8| &7Open gui of kits"
            );
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "give":
                if (!(args.length > 1)) {
                    sender.sendMessage(prefix + lang.getString("error.unknown-command"));
                    return true;
                }
                switch (args[1].toLowerCase()) {
                    case "kit":
                        if (!(args.length > 2)) {
                            sender.sendMessage(prefix + lang.getString("error.unknown-command"));
                            return true;
                        }
                        playerData.getPlayerData(player).addKit(args[2]);
                        break;
                    case "xp":
                        break;
                }

                break;
            case "save":
                for (Player online : Bukkit.getOnlinePlayers()) {
                    playerData.savePlayerData(online);
                }
                player.sendMessage(prefix + lang.getString("messages.save-data"));
                break;
            case "reload":
                lang.reload();
                config.reload();
                kits.reload();
                guis.reload();
                sender.sendMessage(prefix + lang.getString("messages.reload"));
                break;
            case "kits":
                player.openInventory(inventories.openKits(player));
                break;
            case "editmode":
                if (!player.hasMetadata("status")) {
                    return false;
                }
                for (MetadataValue value : player.getMetadata("status")) {
                    if (!value.value().equals(GameStatus.EDIT_MODE)) {
                        player.setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.EDIT_MODE));
                        player.sendMessage(lang.getString("messages.enabled-edit-mode"));
                        return true;
                    }
                    player.setMetadata("status", new FixedMetadataValue(pluginCore.getPlugin(), GameStatus.SPAWN));
                    player.sendMessage(lang.getString("messages.disable-edit-mode"));
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
