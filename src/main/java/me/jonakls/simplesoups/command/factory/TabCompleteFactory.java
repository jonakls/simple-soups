package me.jonakls.simplesoups.command.factory;

import org.bukkit.command.TabCompleter;

public class TabCompleteFactory {

    private final String commandName;
    private final TabCompleter tabCompleter;

    public TabCompleteFactory(String commandName, TabCompleter tabCompleter) {
        this.commandName = commandName;
        this.tabCompleter = tabCompleter;
    }

    public String getCommandName() {
        return commandName;
    }

    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }


}
