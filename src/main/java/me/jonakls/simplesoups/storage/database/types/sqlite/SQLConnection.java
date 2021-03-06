package me.jonakls.simplesoups.storage.database.types.sqlite;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.SimpleSoups;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.storage.database.IConnection;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection implements IConnection {

    private final FileManager config;
    private final SimpleSoups plugin;
    private File file;
    private Connection connection;

    public SQLConnection(PluginCore core) {
        this.plugin = core.getPlugin();
        this.config = core.getFilesLoader().getConfig();
        this.create();
    }

    private void create() {
        this.file = new File(plugin.getDataFolder(), config.getString("database.database-name") + ".db");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void load() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            }
            return connection;
        } catch (SQLException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
