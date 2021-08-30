package me.gardendev.simplesoups.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();

        if (args.length == 1){

            list.add("help");
            list.add("reload");
            list.add("kits");
            list.add("setspawn");
            list.add("spawn");
            list.add("editmode");
        }

        return list;
    }
}
