package me.jonakls.simplesoups.loader;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.SimpleSoups;
import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;


public class ListenersLoader implements Loader {

    private final PluginCore pluginCore;

    public ListenersLoader(PluginCore pluginCore){
        this.pluginCore = pluginCore;
    }

    @Override
    public void load() {
        registerListeners(
                new PlayerJoinListener(pluginCore),
                new PlayerInteractListener(pluginCore),
                new PlayerDropItemListener(pluginCore),
                new InventoryClickListener(pluginCore),
                new FoodChangeListener(pluginCore),
                new EntityDamageListener(pluginCore),
                new SignChangeListener(pluginCore),
                new BlockPlaceListener(pluginCore),
                new PlayerDeathListener(pluginCore),
                new PlayerPickupListener(pluginCore),
                new BlockBreakListener(pluginCore),
                new PlayerQuitListener(pluginCore),
                new ArrowDamageListener(pluginCore)
        );
    }

    public void registerListeners(Listener... listeners){
        PluginManager pluginManager = Bukkit.getPluginManager();
        SimpleSoups plugin = pluginCore.getPlugin();

        for (Listener listener : listeners){
            pluginManager.registerEvents(listener, plugin);
        }
    }
}
