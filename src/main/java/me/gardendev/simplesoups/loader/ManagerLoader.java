package me.gardendev.simplesoups.loader;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.api.Loader;

public class ManagerLoader implements Loader {

    private PluginCore pluginCore;

    public ManagerLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {

    }
}
