package me.jonakls.simplesoups.loader;

import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.manager.KillStreakManager;

public class ManagerLoader implements Loader {

    private KillStreakManager killStreakManager;

    @Override
    public void load() {
        this.killStreakManager = new KillStreakManager();

    }

    public KillStreakManager getKillStreakManager() {
        return killStreakManager;
    }
}
