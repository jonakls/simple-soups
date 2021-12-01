package me.jonakls.simplesoups.storage;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.SimpleSoups;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.storage.cache.DataCache;
import me.jonakls.simplesoups.storage.database.IConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataStorage {

    private final IConnection connection;
    private final SimpleSoups plugin;
    private final String table;
    private final FileManager config;

    public DataStorage(IConnection connection, PluginCore pluginCore) {
        this.plugin = pluginCore.getPlugin();
        this.config = pluginCore.getFilesLoader().getConfig();
        this.table = this.config.getString("database.table");
        this.connection = connection;
        this.initialize();
    }

    final static String SELECT = "SELECT * FROM $table$ WHERE id = '$id$'";

    private void initialize() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ")
                .append(table)
                .append("(id VARCHAR(36) PRIMARY KEY,")
                .append("name VARCHAR(60),")
                .append("kills INTEGER,")
                .append("deaths INTEGER,")
                .append("xp INTEGER,")
                .append("kdr DECIMAL,")
                .append("kits VARCHAR (200))");

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(sql.toString());
            statement.execute();
            plugin.getLogger().info("Loading database success!");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insert(DataCache cache) {

        String sqlInsert = "REPLACE INTO $table$ (id, name, kills, deaths, xp, kdr, kits) VALUES (?, ?, ?, ?, ?, ?, ?)"
                .replace("$table$", table);

        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(sqlInsert);
            statement.setString(1, cache.getUuid());
            statement.setString(2, cache.getPlayerName());
            statement.setInt(3, cache.getKills());
            statement.setInt(4, cache.getDeaths());
            statement.setInt(5, cache.getXp());
            statement.setFloat(6, cache.getKdr());
            statement.setString(7, cache.getStringKits());

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

    public String[] getKits(String id) {
        String[] def = {config.getString("kit-default")};
        try (Connection con = connection.getConnection()) {
            PreparedStatement statement = con.prepareStatement(
                    SELECT.replace("$table$", table).replace("$id$", id)
            );

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                if(result.getString("id").equalsIgnoreCase(id.toLowerCase())) {
                    return result.getString("kits").split(",");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return def;
    }
}
