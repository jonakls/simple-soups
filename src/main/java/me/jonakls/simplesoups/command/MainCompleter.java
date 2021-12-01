package me.jonakls.simplesoups.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            return List.of("reload");
        }

        if (args.length == 1){

            return List.of(
                    "reload",
                    "give",
                    "save",
                    "kits",
                    "editmode",
                    "setspawn",
                    "spawn",
                    "help"
            );
        }
        return null;
    }
}
