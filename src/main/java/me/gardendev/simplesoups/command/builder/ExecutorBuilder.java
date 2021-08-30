package me.gardendev.simplesoups.command.builder;

import org.bukkit.command.CommandExecutor;

public class ExecutorBuilder {

    private final String commandName;
    private final CommandExecutor commandExecutor;

    public ExecutorBuilder(String commandName, CommandExecutor commandExecutor) {
        this.commandName = commandName;
        this.commandExecutor = commandExecutor;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
