package me.gardendev.simplesoups.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class FileManager extends YamlConfiguration {

    private final String fileName;

    private final Plugin plugin;

    private final File file;

    public FileManager(Plugin plugin, String fileName, String fileExtension, File folder) {
        this.plugin = plugin;
        this.fileName = fileName + (fileName.endsWith(fileExtension) ? "" : fileExtension);
        this.file = new File(folder, fileName);
        createFile();
    }

    public FileManager(Plugin plugin, String fileName) {
        this(plugin, fileName, ".yml");
    }

    public FileManager(Plugin plugin, String fileName, String fileExtension) {
        this(plugin, fileName, fileExtension, plugin.getDataFolder());
    }

    private void createFile() {
        try {
            if (file.exists()) {
                load(file);
                save(file);
                return;
            }
            if (this.plugin.getResource(this.fileName) != null) {
                this.plugin.saveResource(this.fileName, false);
            } else {
                save(file);
            }
            load(file);
        } catch (InvalidConfigurationException | IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Creation of Configuration '" + this.fileName + "' failed.", e);
        }
    }

    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
        }
    }

    public void reload() {
        try {

            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
        }
    }

    @Override
    public String getString(String path){
        String text = super.getString(path);

        if (text == null){
            plugin.getLogger().info("Error: Path is null: " + path);
            return "Error 404 - The path is null: " + path;
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> list = super.getStringList(path);
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line));
        return list;
    }

    public Material getMaterial(String path) {
        try{
            return Material.valueOf(super.getString(path));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Material.BEDROCK;
        }
    }
}