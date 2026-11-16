
package com.conectacomercio.dao;

import com.conectacomercio.config.ConexionDB;
import com.conectacomercio.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de realizar las operaciones CRUD
 * de productos en la base de datos ConectaComercio.
 */
public class ProductoDAO {

    /**
     * CREATE: registra un nuevo producto.
     */
    public boolean insertar(Producto producto) throws SQLException {

        String sql = """
                INSERT INTO productos
                (nombre, descripcion, precio, stock, id_mayorista)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setInt(5, producto.getIdMayorista());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * READ: consulta todos los productos.
     */
    public List<Producto> consultarTodos() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = """
                SELECT id, nombre, descripcion, precio, stock, id_mayorista
                FROM productos
                ORDER BY id
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql);
             ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getString("descripcion"),
                        resultado.getBigDecimal("precio"),
                        resultado.getInt("stock"),
                        resultado.getInt("id_mayorista")
                );

                productos.add(producto);
            }
        }

        return productos;
    }

    /**
     * UPDATE: actualiza un producto existente.
     */
    public boolean actualizar(Producto producto) throws SQLException {

        String sql = """
                UPDATE productos
                SET nombre = ?,
                    descripcion = ?,
                    precio = ?,
                    stock = ?,
                    id_mayorista = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setInt(5, producto.getIdMayorista());
            statement.setInt(6, producto.getId());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * DELETE: elimina un producto por su identificador.
     */
    public boolean eliminar(int id) throws SQLException {

        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}
