package me.gardendev.simplesoups.command.builder;

import org.bukkit.command.TabCompleter;

public class TabCompleteBuilder {

    private final String commandName;
    private final TabCompleter tabCompleter;

    public TabCompleteBuilder(String commandName, TabCompleter tabCompleter) {
        this.commandName = commandName;
        this.tabCompleter = tabCompleter;
    }

    public String getCommandName(){
        return commandName;
    }

    public TabCompleter getTabCompleter(){
        return tabCompleter;
    }


}
