package me.gardendev.simplesoups.loader;

import me.gardendev.simplesoups.api.Loader;
import me.gardendev.simplesoups.manager.KillStreakManager;

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
