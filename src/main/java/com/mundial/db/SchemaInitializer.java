package com.mundial.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {

    public static void createTables() throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS GRUPO (id INTEGER PRIMARY KEY, nombre TEXT NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS EQUIPO (id INTEGER PRIMARY KEY, nombre TEXT NOT NULL, país TEXT NOT NULL, grupo_id INTEGER)");
            statement.execute("CREATE TABLE IF NOT EXISTS PARTIDO (id INTEGER PRIMARY KEY, local_id INTEGER, visitante_id INTEGER ," +
                    " grupo_id INTEGER, fase TEXT , fecha TEXT, goles_local INTEGER, goles_visitante INTEGER, estado TEXT)");
        }
    }
}
