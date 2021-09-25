package me.gardendev.simplesoups;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleSoups extends JavaPlugin {

    private final PluginCore core = new PluginCore(this);

    @Override
    public void onEnable() {
        getLogger().info("Loading SoupLab v" + getDescription().getVersion());
        this.core.init();
    }

    @Override
    public void onDisable() {
        getLogger().info("Closing SoupLab v" + getDescription().getVersion());
        getLogger().info("Saving player data...");
        //this.core.getPlayerData();
        this.core.closeDatabase();
        getLogger().info("Closed connection, finally!");
    }
}
