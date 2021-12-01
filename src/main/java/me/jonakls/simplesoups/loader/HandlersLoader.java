package me.jonakls.simplesoups.loader;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.handlers.DeathMessagesHandler;
import me.jonakls.simplesoups.handlers.KillStreakHandler;

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
