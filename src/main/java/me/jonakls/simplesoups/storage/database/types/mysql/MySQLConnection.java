package me.jonakls.simplesoups.storage.database.types.mysql;

import me.jonakls.simplesoups.PluginCore;
import me.jonakls.simplesoups.manager.FileManager;
import me.jonakls.simplesoups.storage.database.IConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection implements IConnection {

    private Connection connection;
    public FileManager config;

    public MySQLConnection(PluginCore core) {
        this.config = core.getFilesLoader().getConfig();
    }

    final static String URL_CONNECTION = "jdbc:mysql://{1}:{2}/{3}";

    @Override
    public void load() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        URL_CONNECTION
                                .replace("{1}", config.getString("database.host"))
                                .replace("{2}", String.valueOf(config.getInt("database.port")))
                                .replace("{3}", config.getString("database.database-name")),
                        config.getString("database.user"),
                        config.getString("database.password")
                );
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
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
