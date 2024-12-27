package com.pustakadigital.databaseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:pustaka.db";
    private static Connection connection = null;

    public static final String TABLE_BUKU = "buku";
    public static final String TABLE_USERS = "users";

    public static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null; // Set to null after closing
            }
        } catch (SQLException e) {
            System.out.println("Error closing database: " + e.getMessage());

        }
    }
}