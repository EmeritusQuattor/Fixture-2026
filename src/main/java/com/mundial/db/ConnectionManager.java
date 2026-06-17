package com.mundial.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) conn = DriverManager.getConnection("jdbc:sqlite:mundial2026.db");
        return conn;
    }

}
