package com.panosen.orm;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;

public class ConnectionManager {

    private final DataSource dataSource;

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    public Connection getConnection() throws Exception {
        Connection connection = threadLocalConnection.get();
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        connection = dataSource.getConnection();
        threadLocalConnection.set(connection);
        return connection;
    }
}
