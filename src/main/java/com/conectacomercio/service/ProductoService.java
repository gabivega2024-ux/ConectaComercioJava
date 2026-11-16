
package com.conectacomercio.service;

import com.conectacomercio.dao.ProductoDAO;
import com.conectacomercio.model.Producto;

import java.sql.SQLException;
import java.util.List;

/**
 * Contiene la lógica de negocio y validaciones
 * para la gestión de productos.
 */
public class ProductoService {

    private final ProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }

    /**
     * CREATE: registra un producto después de validar sus datos.
     */
    public boolean registrarProducto(Producto producto) throws SQLException {

        validarProducto(producto);

        return productoDAO.insertar(producto);
    }

    /**
     * READ: obtiene todos los productos registrados.
     */
    public List<Producto> listarProductos() throws SQLException {

        return productoDAO.consultarTodos();
    }

    /**
     * UPDATE: actualiza los datos de un producto.
     */
    public boolean actualizarProducto(Producto producto) throws SQLException {

        if (producto.getId() <= 0) {
            throw new IllegalArgumentException(
                    "El ID del producto debe ser mayor que cero."
            );
        }

        validarProducto(producto);

        return productoDAO.actualizar(producto);
    }

    /**
     * DELETE: elimina un producto por su ID.
     */
    public boolean eliminarProducto(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID debe ser mayor que cero."
            );
        }

        return productoDAO.eliminar(id);
    }

    /**
     * Valida los datos obligatorios del producto.
     */
    private void validarProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede estar vacío."
            );
        }

        if (producto.getNombre() == null ||
                producto.getNombre().isBlank()) {

            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio."
            );
        }

        if (producto.getNombre().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar los 100 caracteres."
            );
        }

        if (producto.getPrecio() == null ||
                producto.getPrecio().signum() <= 0) {

            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }

        if (producto.getStock() < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo."
            );
        }

        if (producto.getIdMayorista() <= 0) {
            throw new IllegalArgumentException(
                    "El ID del mayorista debe ser mayor que cero."
            );
        }
    }
}
