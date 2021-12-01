package me.jonakls.simplesoups.loader;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.command.MainCommand;
import me.jonakls.simplesoups.command.MainCompleter;
import me.jonakls.simplesoups.command.builder.ExecutorBuilder;
import me.jonakls.simplesoups.command.builder.TabCompleteBuilder;
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
