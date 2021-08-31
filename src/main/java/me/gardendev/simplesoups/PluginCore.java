package me.gardendev.simplesoups;

import me.gardendev.simplesoups.gui.KitsGUI;
import me.gardendev.simplesoups.handlers.KillStreakHandler;
import me.gardendev.simplesoups.loader.*;
import me.gardendev.simplesoups.manager.KillStreakManager;
import me.gardendev.simplesoups.storage.PlayerCache;
import me.gardendev.simplesoups.api.Core;
import me.gardendev.simplesoups.api.Loader;
import me.gardendev.simplesoups.scoreboard.GameScoreboard;
import me.gardendev.simplesoups.storage.DataStorage;
import me.gardendev.simplesoups.storage.database.IConnection;
import me.gardendev.simplesoups.storage.database.types.sqlite.SQLConnection;

public class PluginCore implements Core{

    private final SimpleSoups plugin;

    private final FilesLoader filesLoader = new FilesLoader(this);
    private final ManagerLoader managerLoader = new ManagerLoader();
    private final HandlersLoader handlersLoader = new HandlersLoader(this);

    private KitsGUI kitsGUI;
    private PlayerCache playerCache;
    private IConnection connection;
    private DataStorage storage;
    private GameScoreboard gameScoreboard;

    public PluginCore(SimpleSoups plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {

        initLoaders(
                filesLoader,
                new CommandsLoader(this),
                new ListenersLoader(this),
                managerLoader,
                handlersLoader
        );

        this.database();
        kitsGUI = new KitsGUI(filesLoader);
        playerCache = new PlayerCache(this);
        gameScoreboard = new GameScoreboard(this);
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

    private void initLoaders(Loader...loaders){
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

    public ManagerLoader getManagers() {
        return managerLoader;
    }

    public KitsGUI getKitsGUI() {
        return kitsGUI;
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

    public HandlersLoader getHandlersLoader() {
        return handlersLoader;
    }
}
