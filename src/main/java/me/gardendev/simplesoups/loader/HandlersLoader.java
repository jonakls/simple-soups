package me.gardendev.simplesoups.loader;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.api.Loader;
import me.gardendev.simplesoups.handlers.DeathMessagesHandler;
import me.gardendev.simplesoups.handlers.KillStreakHandler;

public class HandlersLoader implements Loader {

    private final PluginCore pluginCore;
    private KillStreakHandler killStreakHandler;
    private DeathMessagesHandler deathMessagesHandler;

    public HandlersLoader(PluginCore pluginCore) {
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        this.killStreakHandler = new KillStreakHandler(pluginCore);
        this.deathMessagesHandler = new DeathMessagesHandler(pluginCore);

    }

    public KillStreakHandler getKillStreakHandler() {
        return killStreakHandler;
    }

    public DeathMessagesHandler getDeathMessagesHandler() {
        return deathMessagesHandler;
    }
}
