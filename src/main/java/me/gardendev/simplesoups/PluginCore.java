package me.gardendev.simplesoups;

import me.gardendev.simplesoups.gui.KitsGUI;
import me.gardendev.simplesoups.handlers.KillStreakHandler;
import me.gardendev.simplesoups.loader.CommandsLoader;
import me.gardendev.simplesoups.loader.ManagerLoader;
import me.gardendev.simplesoups.manager.KillStreakManager;
import me.gardendev.simplesoups.storage.PlayerCache;
import me.gardendev.simplesoups.api.Core;
import me.gardendev.simplesoups.api.Loader;
import me.gardendev.simplesoups.loader.FilesLoader;
import me.gardendev.simplesoups.loader.ListenersLoader;
import me.gardendev.simplesoups.scoreboard.GameScoreboard;
import me.gardendev.simplesoups.storage.DataStorage;
import me.gardendev.simplesoups.storage.database.IConnection;
import me.gardendev.simplesoups.storage.database.types.sqlite.SQLConnection;

public class PluginCore implements Core{

    private final SimpleSoups plugin;

    private FilesLoader filesLoader;
    private ManagerLoader managerLoader;

    private KitsGUI kitsGUI;
    private KillStreakManager killStreakManager;
    private KillStreakHandler killStreakHandler;
    private PlayerCache playerCache;
    private IConnection connection;
    private DataStorage storage;
    private GameScoreboard gameScoreboard;

    public PluginCore(SimpleSoups plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        filesLoader = new FilesLoader(plugin);
        filesLoader.load();

        this.database();
        kitsGUI = new KitsGUI(filesLoader);
        killStreakManager = new KillStreakManager();
        killStreakHandler = new KillStreakHandler(filesLoader, killStreakManager);
        playerCache = new PlayerCache(this);
        gameScoreboard = new GameScoreboard(this);


        managerLoader = new ManagerLoader(this);
        managerLoader.load();

        initLoaders(
                new CommandsLoader(this),
                new ListenersLoader(this));
    }

    private void database() {
        this.getPlugin().getLogger().info("Loading database...");
        this.connection = new SQLConnection(this);
        this.connection.load();

        this.storage = new DataStorage(this.connection, this);
    }

    public void closeDatabase() {
        this.connection.close();
    }

    private void initLoaders(Loader... loaders){
        for (Loader loader : loaders){
            loader.load();
        }
    }


    public FilesLoader getFilesLoader() {
        return filesLoader;
    }

    public SimpleSoups getPlugin(){
        return plugin;
    }

    public ManagerLoader getManagers(){
        return managerLoader;
    }

    public KitsGUI getKitsGUI() {
        return kitsGUI;
    }

    public KillStreakManager getKillStreak() {
        return killStreakManager;
    }

    public KillStreakHandler getKillStreakHandler(){
        return killStreakHandler;
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }

    public DataStorage getStorage() {
        return storage;
    }

    public GameScoreboard gameScoreboard(){
        return gameScoreboard;
    }

}
