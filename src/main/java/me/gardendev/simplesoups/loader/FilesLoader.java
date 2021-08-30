package me.gardendev.simplesoups.loader;

import me.gardendev.simplesoups.SimpleSoups;
import me.gardendev.simplesoups.manager.FileManager;
import me.gardendev.simplesoups.api.Loader;

public class FilesLoader implements Loader {

    private final SimpleSoups plugin;
    private FileManager config;
    private FileManager gui;
    private FileManager lang;
    private FileManager kits;

    public FilesLoader(SimpleSoups plugin){
        this.plugin = plugin;
    }

    @Override
    public void load() {
        config = new FileManager(plugin, "config.yml");
        gui = new FileManager(plugin, "gui.yml");
        lang = new FileManager(plugin , "lang.yml");
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
