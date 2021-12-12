package me.jonakls.simplesoups.loader;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.command.MainCommand;
import me.jonakls.simplesoups.command.MainCompleter;
import me.jonakls.simplesoups.command.factory.ExecutorFactory;
import me.jonakls.simplesoups.command.factory.TabCompleteFactory;
import org.bukkit.Bukkit;

public class CommandsLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandsLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        registerCommands(
                new ExecutorFactory("soup", new MainCommand(pluginCore))
        );

        registerTabCompleter(
                new TabCompleteFactory("soup", new MainCompleter())
        );
    }

    public void registerCommands(ExecutorFactory... executorFactories) {

        for (ExecutorFactory executorFactory : executorFactories) {
            Bukkit.getPluginCommand(executorFactory.getCommandName()).setExecutor(executorFactory.getCommandExecutor());
        }
    }

    public void registerTabCompleter(TabCompleteFactory... tabCompleteFactories) {

        for (TabCompleteFactory tabCompleteFactory : tabCompleteFactories) {
            Bukkit.getPluginCommand(tabCompleteFactory.getCommandName()).setTabCompleter(tabCompleteFactory.getTabCompleter());
        }
    }
}
