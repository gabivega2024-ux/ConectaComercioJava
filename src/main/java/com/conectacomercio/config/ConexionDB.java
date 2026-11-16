
package com.conectacomercio.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión JDBC con MariaDB.
 */
public class ConexionDB {

    private static final String URL =
            "jdbc:mariadb://localhost:3306/conectacomercio_api";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    /**
     * Establece la conexión con la base de datos.
     */
    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}
