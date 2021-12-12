package me.jonakls.simplesoups;

import me.jonakls.simplesoups.api.Core;
import me.jonakls.simplesoups.api.Loader;
import me.jonakls.simplesoups.gui.Inventories;
import me.jonakls.simplesoups.loader.CommandsLoader;
import me.jonakls.simplesoups.loader.FilesLoader;
import me.jonakls.simplesoups.loader.HandlersLoader;
import me.jonakls.simplesoups.loader.ListenersLoader;
import me.jonakls.simplesoups.loader.ManagerLoader;
import me.jonakls.simplesoups.scoreboard.GameScoreboard;
import me.jonakls.simplesoups.storage.DataStorage;
import me.jonakls.simplesoups.storage.PlayerData;
import me.jonakls.simplesoups.storage.database.IConnection;
import me.jonakls.simplesoups.storage.database.types.mysql.MySQLConnection;
import me.jonakls.simplesoups.storage.database.types.sqlite.SQLConnection;

public class PluginCore implements Core {

    private final SimpleSoups plugin;

    private FilesLoader filesLoader;
    private ManagerLoader managerLoader;
    private HandlersLoader handlersLoader;

    private Inventories inventories;
    private PlayerData playerData;
    private IConnection connection;
    private DataStorage storage;
    private GameScoreboard gameScoreboard;

    public PluginCore(SimpleSoups plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        this.filesLoader = new FilesLoader(this);
        this.filesLoader.load();
        this.database();
        playerData = new PlayerData(this);
        initLoaders(
                this.managerLoader = new ManagerLoader(),
                this.handlersLoader = new HandlersLoader(this),
                new CommandsLoader(this),
                new ListenersLoader(this)
        );
        inventories = new Inventories(this);
        gameScoreboard = new GameScoreboard(this);
        gameScoreboard.runTaskUpdate();
    }

    private void database() {
        this.getPlugin().getLogger().info("Loading database...");
        if (this.filesLoader.getConfig().getBoolean("database.enable")) {
            this.connection = new MySQLConnection(this);
        } else {
            this.connection = new SQLConnection(this);
        }
        this.connection.load();
        this.storage = new DataStorage(this.connection, this);
    }

    public void closeDatabase() {
        this.connection.close();
    }

    private void initLoaders(Loader... loaders) {
        for (Loader loader : loaders) {
            loader.load();
        }
    }

    public FilesLoader getFilesLoader() {
        return filesLoader;
    }

    public SimpleSoups getPlugin() {
        return plugin;
    }

    public ManagerLoader getManagers() {
        return managerLoader;
    }

    public Inventories getInventories() {
        return inventories;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public DataStorage getStorage() {
        return storage;
    }

    public GameScoreboard gameScoreboard() {
        return gameScoreboard;
    }

    public HandlersLoader getHandlersLoader() {
        return handlersLoader;
    }
}
