package me.jonakls.simplesoups.loader;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.SimpleSoups;
import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.manager.FileManager;

public class FilesLoader implements Loader {

    private final SimpleSoups plugin;
    private FileManager config;
    private FileManager gui;
    private FileManager lang;
    private FileManager kits;

    public FilesLoader(PluginCore pluginCore) {
        this.plugin = pluginCore.getPlugin();
    }

    @Override
    public void load() {
        config = new FileManager(plugin, "config.yml");
        gui = new FileManager(plugin, "gui.yml");
        lang = new FileManager(plugin, "lang.yml");
        kits = new FileManager(plugin, "kits.yml");
    }


    public FileManager getConfig() {
        return config;
    }

    public FileManager getGui() {
        return gui;
    }

    public FileManager getLang() {
        return lang;
    }

    public FileManager getKits() {
        return kits;
    }
}
