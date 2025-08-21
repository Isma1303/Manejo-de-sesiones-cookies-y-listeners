package com.darwinruiz.shoplite.databaseshoplite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin123";

    public static Connection connection;

    private ConnectionDB() {
    }

    public static Connection getConnection() {
        try {
            // Cargar el driver de PostgreSQL explícitamente
            Class.forName("org.postgresql.Driver");

            // Crear nueva conexión si no existe o está cerrada
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión a PostgreSQL establecida exitosamente");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver PostgreSQL no encontrado: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo abrir conexión a PostgreSQL: " + e.getMessage(), e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexión a PostgreSQL cerrada");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    // Método para verificar si la conexión está activa
    public static boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

}