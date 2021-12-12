package me.jonakls.simplesoups.command.factory;

import org.bukkit.command.CommandExecutor;

public class ExecutorFactory {

    private final String commandName;
    private final CommandExecutor commandExecutor;

    public ExecutorFactory(String commandName, CommandExecutor commandExecutor) {
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
