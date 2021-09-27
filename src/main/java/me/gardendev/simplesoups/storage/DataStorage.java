package me.gardendev.simplesoups.storage;

import me.gardendev.simplesoups.PluginCore;
import me.gardendev.simplesoups.storage.cache.DataCache;
import me.gardendev.simplesoups.storage.database.IConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStorage {

    private final IConnection connection;
    private final PluginCore pluginCore;
    private final String table;

    public DataStorage(IConnection connection, PluginCore pluginCore) {
        this.pluginCore = pluginCore;
        this.table = pluginCore.getFilesLoader().getConfig().getString("database.table");
        this.connection = connection;
        this.initialize();
    }

    final static String SELECT = "SELECT * FROM $table$ WHERE id = '$id$'";

    private void initialize() {

        String sql = "CREATE TABLE IF NOT EXISTS $table$ (id VARCHAR(36) PRIMARY KEY, name VARCHAR(60), kills INTEGER, deaths INTEGER, xp INTEGER, kdr DECIMAL)"
                .replace("$table$", table);

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
            pluginCore.getPlugin().getLogger().info("Loading database success!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(DataCache cache) {

        String sqlInsert = "REPLACE INTO $table$ (id, name, kills, deaths, xp, kdr) VALUES (?, ?, ?, ?, ?, ?)"
                .replace("$table$", table);

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(sqlInsert);
            statement.setString(1, cache.getUuid());
            statement.setString(2, cache.getPlayerName());
            statement.setInt(3, cache.getKills());
            statement.setInt(4, cache.getDeaths());
            statement.setInt(5, cache.getXp());
            statement.setFloat(6, cache.getKdr());

            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getKills(String id) {

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(
                    SELECT.replace("$table$", table).replace("$id$", id)
            );
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                if(result.getString("id").equalsIgnoreCase(id.toLowerCase())) {
                    return result.getInt("kills");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDeaths(String id) {

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(
                    SELECT.replace("$table$", table).replace("$id$", id)
            );

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

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(
                    SELECT.replace("$table$", table).replace("$id$", id)
            );

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

    public float getKDR(String id) {
        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(
                    SELECT.replace("$table$", table).replace("$id$", id)
            );

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(result.getString("id").equalsIgnoreCase(id.toLowerCase())) {
                    return result.getInt("kdr");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
