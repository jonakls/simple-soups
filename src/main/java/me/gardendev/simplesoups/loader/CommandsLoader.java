package me.gardendev.simplesoups.loader;

import me.gardendev.simplesoups.command.MainCompleter;
import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.command.MainCommand;
import me.gardendev.simplesoups.command.builder.ExecutorBuilder;
import me.gardendev.simplesoups.api.Loader;
import me.gardendev.simplesoups.command.builder.TabCompleteBuilder;
import org.bukkit.Bukkit;

public class CommandsLoader implements Loader {

    private final PluginCore pluginCore;

    public CommandsLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        registerCommands(
                new ExecutorBuilder("soup", new MainCommand(pluginCore))
        );

        registerTabCompleter(
                new TabCompleteBuilder("soup", new MainCompleter())
        );
    }

    public void registerCommands(ExecutorBuilder... executorBuilders){

        for (ExecutorBuilder executorBuilder : executorBuilders){
            Bukkit.getPluginCommand(executorBuilder.getCommandName()).setExecutor(executorBuilder.getCommandExecutor());
        }
    }

    public void registerTabCompleter(TabCompleteBuilder...tabCompleteBuilders) {

        for (TabCompleteBuilder tabCompleteBuilder : tabCompleteBuilders) {
            Bukkit.getPluginCommand(tabCompleteBuilder.getCommandName()).setTabCompleter(tabCompleteBuilder.getTabCompleter());
        }
    }
}
