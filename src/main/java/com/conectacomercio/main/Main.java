
package com.conectacomercio.main;

import com.conectacomercio.model.Producto;
import com.conectacomercio.service.ProductoService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Programa principal de ConectaComercio Java.
 * Permite administrar productos mediante un menú CRUD.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductoService productoService =
            new ProductoService();

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("       CONECTACOMERCIO JAVA");
        System.out.println("       GESTION DE PRODUCTOS");
        System.out.println("====================================");

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarProducto();
                    case 2 -> consultarProductos();
                    case 3 -> actualizarProducto();
                    case 4 -> eliminarProducto();
                    case 0 -> System.out.println(
                            "\nGracias por utilizar ConectaComercio Java."
                    );
                    default -> System.out.println(
                            "\nOpción no válida."
                    );
                }

            } catch (SQLException e) {
                System.out.println(
                        "\nError en la base de datos: " + e.getMessage()
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "\nValidación: " + e.getMessage()
                );
            }

        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Muestra las opciones del menú principal.
     */
    private static void mostrarMenu() {

        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Registrar producto");
        System.out.println("2. Consultar productos");
        System.out.println("3. Actualizar producto");
        System.out.println("4. Eliminar producto");
        System.out.println("0. Salir");
        System.out.println("====================================");
    }

    /**
     * Registra un nuevo producto.
     */
    private static void registrarProducto() throws SQLException {

        System.out.println("\n--- REGISTRAR PRODUCTO ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Precio: ");
        BigDecimal precio = leerDecimal();

        int stock = leerEntero("Stock: ");

        int idMayorista = leerEntero("ID del mayorista: ");

        Producto producto = new Producto(
                nombre,
                descripcion,
                precio,
                stock,
                idMayorista
        );

        boolean registrado =
                productoService.registrarProducto(producto);

        if (registrado) {
            System.out.println(
                    "\nProducto registrado correctamente."
            );
        }
    }

    /**
     * Consulta y muestra todos los productos.
     */
    private static void consultarProductos() throws SQLException {

        System.out.println("\n--- LISTADO DE PRODUCTOS ---");

        List<Producto> productos =
                productoService.listarProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        System.out.printf(
                "%-5s %-20s %-15s %-10s %-12s%n",
                "ID", "NOMBRE", "PRECIO", "STOCK", "MAYORISTA"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Producto producto : productos) {

            System.out.printf(
                    "%-5d %-20s %-15s %-10d %-12d%n",
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getIdMayorista()
            );
        }
    }

    /**
     * Actualiza un producto existente.
     */
    private static void actualizarProducto() throws SQLException {

        System.out.println("\n--- ACTUALIZAR PRODUCTO ---");

        int id = leerEntero("ID del producto que desea actualizar: ");

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nueva descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Nuevo precio: ");
        BigDecimal precio = leerDecimal();

        int stock = leerEntero("Nuevo stock: ");

        int idMayorista = leerEntero("ID del mayorista: ");

        Producto producto = new Producto(
                id,
                nombre,
                descripcion,
                precio,
                stock,
                idMayorista
        );

        boolean actualizado =
                productoService.actualizarProducto(producto);

        if (actualizado) {
            System.out.println(
                    "\nProducto actualizado correctamente."
            );
        } else {
            System.out.println(
                    "\nNo se encontró el producto indicado."
            );
        }
    }

    /**
     * Elimina un producto por su identificador.
     */
    private static void eliminarProducto() throws SQLException {

        System.out.println("\n--- ELIMINAR PRODUCTO ---");

        int id = leerEntero("ID del producto que desea eliminar: ");

        System.out.print(
                "¿Está seguro de eliminarlo? (S/N): "
        );

        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            boolean eliminado = productoService.eliminarProducto(id);

            if (eliminado) {
                System.out.println(
                        "\nProducto eliminado correctamente."
                );
            } else {
                System.out.println(
                        "\nNo se encontró el producto indicado."
                );
            }

        } else {
            System.out.println("\nOperación cancelada.");
        }
    }

    /**
     * Lee números enteros evitando entradas inválidas.
     */
    private static int leerEntero(String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println(
                        "Ingrese un número entero válido."
                );
            }
        }
    }

    /**
     * Lee valores decimales para precios.
     */
    private static BigDecimal leerDecimal() {

        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.print(
                        "Precio inválido. Ingrese nuevamente: "
                );
            }
        }
    }
}
