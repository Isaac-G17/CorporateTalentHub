package com.riwi.talent.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase de utilidad para obtener conexiones JDBC hacia PostgreSQL.
 * Los datos de conexión se leen de db.properties (src/main/resources).
 */
public final class ConexionBD {

    private static final String ARCHIVO_CONFIG = "db.properties";
    private static final Properties PROPIEDADES = cargarPropiedades();

    private ConexionBD() {
    }

    private static Properties cargarPropiedades() {
        var propiedades = new Properties();

        try (InputStream entrada = ConexionBD.class.getClassLoader()
                .getResourceAsStream(ARCHIVO_CONFIG)) {

            if (entrada == null) {
                throw new IllegalStateException(
                        "No se encontró " + ARCHIVO_CONFIG + " en el classpath.");
            }

            propiedades.load(entrada);
        } catch (IOException excepcion) {
            throw new IllegalStateException(
                    "No fue posible cargar la configuración de la base de datos.",
                    excepcion);
        }

        return propiedades;
    }

    /*
     * Estilo Legacy (Java 8 y anteriores):
     * Connection, PreparedStatement y ResultSet debían cerrarse a mano
     * dentro de un bloque finally, comprobando cada referencia por null
     * y capturando la SQLException propia de cada close():
     *
     *   Connection conexion = null;
     *   PreparedStatement sentencia = null;
     *   ResultSet resultado = null;
     *   try {
     *       conexion = DriverManager.getConnection(url, usuario, clave);
     *       sentencia = conexion.prepareStatement(sql);
     *       resultado = sentencia.executeQuery();
     *       // ... procesar resultado
     *   } catch (SQLException e) {
     *       // manejo del error
     *   } finally {
     *       if (resultado != null) { try { resultado.close(); } catch (SQLException e) {} }
     *       if (sentencia != null) { try { sentencia.close(); } catch (SQLException e) {} }
     *       if (conexion != null) { try { conexion.close(); } catch (SQLException e) {} }
     *   }
     *
     * Si se olvidaba cerrar alguno de estos recursos, o una excepción
     * ocurría entre la apertura y el cierre, la conexión quedaba abierta
     * indefinidamente: eso agota el pool de conexiones de la base de
     * datos (fuga de memoria / Memory Leak) y provoca errores de
     * "too many connections" bajo carga.
     */

    /*
     * Estilo Moderno (Java 17/21):
     * Connection, PreparedStatement y ResultSet implementan AutoCloseable.
     * Declarándolos dentro de un try-with-resources (ver EmpleadoDAOImpl),
     * el compilador genera automáticamente el equivalente al finally
     * anterior: cada recurso se cierra al salir del bloque, en orden
     * inverso al de su declaración, incluso si se lanza una excepción.
     * Esto elimina el riesgo de recursos olvidados y por lo tanto
     * previene las fugas de memoria asociadas a conexiones no cerradas.
     */
    public static Connection obtenerConexion() throws SQLException {
        var url = PROPIEDADES.getProperty("db.url");
        var usuario = PROPIEDADES.getProperty("db.user");
        var clave = PROPIEDADES.getProperty("db.password");

        return DriverManager.getConnection(url, usuario, clave);
    }
}
