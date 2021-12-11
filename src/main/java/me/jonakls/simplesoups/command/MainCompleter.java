package me.jonakls.simplesoups.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> subCommands = new ArrayList<>();

        if (!(sender instanceof Player)) {
            subCommands.add("reload");
            return subCommands;
        }

        if (args.length == 1){

            subCommands.add("reload");
            subCommands.add("give");
            subCommands.add("save");
            subCommands.add("kits");
            subCommands.add("editmode");
            subCommands.add("setspawn");
            subCommands.add("help");
            return subCommands;
        }
        return null;
    }
}
