package me.jonakls.simplesoups.storage.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnection {

    void load();

    Connection getConnection() throws SQLException;

    void close();

}
