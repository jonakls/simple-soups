package me.gardendev.simplesoups.storage;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.manager.FileManager;
import me.gardendev.simplesoups.storage.database.IConnection;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStorage {

    private final IConnection connection;
    private final PluginCore pluginCore;
    private final FileManager config;

    public DataStorage(IConnection connection, PluginCore pluginCore) {
        this.pluginCore = pluginCore;
        this.config = pluginCore.getFilesLoader().getConfig();
        this.connection = connection;
        this.initialize();
    }

    private void initialize() {
        try {
            Connection con = connection.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + config.getString("database.table") +
                            " (id VARCHAR(36) PRIMARY KEY," +
                            " name VARCHAR(60)," +
                            " kills INTEGER," +
                            " deaths INTEGER," +
                            " xp INTEGER)"
            );
            statement.execute();
            pluginCore.getPlugin().getLogger().info("Loading database success!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(PlayerCache cache, Player player) {
        try {
            Connection con = connection.getConnection();
            PreparedStatement statement = con.prepareStatement(
                    "REPLACE INTO " + config.getString("database.table") + " (id, name, kills, deaths, xp) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, cache.getId(player).toString());
            statement.setString(2, player.getName());
            statement.setInt(3, cache.getKills(player));
            statement.setInt(4, cache.getDeaths(player));
            statement.setInt(5, cache.getXp(player));

            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getKills(String id) {
        try {
            Connection con = connection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM " + config.getString("database.table") + " WHERE id = '" + id + "'");

            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getString("id").equalsIgnoreCase(id.toLowerCase())) {
                    pluginCore.getPlugin().getLogger().info("Kills: " + result.getInt("kills"));
                    return result.getInt("kills");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDeaths(String id) {
        try {
            Connection con = connection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM " + config.getString("database.table") + " WHERE id = '" + id + "'");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(result.getString("id").equalsIgnoreCase(id.toLowerCase())) {
                    return result.getInt("deaths");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getXp(String id) {
        try {
            Connection con = connection.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM " + config.getString("database.table") + " WHERE id = '" + id + "'");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(result.getString("id").equalsIgnoreCase(id.toLowerCase())) {
                    return result.getInt("xp");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
