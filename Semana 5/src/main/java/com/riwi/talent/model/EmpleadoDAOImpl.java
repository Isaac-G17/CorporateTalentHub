package com.riwi.talent.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementación JDBC de EmpleadoDAO. Desarrollador y Gerente se
 * persisten en una misma tabla ("empleados") con una columna
 * discriminadora "rol"; las columnas propias de cada subtipo
 * (lenguaje_principal, presupuesto_mensual) quedan NULL para el otro
 * rol. Todas las consultas usan PreparedStatement y todos los recursos
 * JDBC se abren en try-with-resources.
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    private record DatosRol(String rol, String lenguajePrincipal, Double presupuestoMensual) {
    }

    /*
     * Sintaxis Moderna (Java 21): switch expression con Pattern Matching
     * sobre una sealed class. Como Empleado solo permite Desarrollador y
     * Gerente, el compilador verifica que estos dos casos cubren todas
     * las implementaciones posibles: no hace falta (ni se permite dejar
     * vacía) una rama default, y si en el futuro se agrega un nuevo
     * permits este switch dejaría de compilar hasta contemplarlo.
     */
    private DatosRol extraerDatosRol(Empleado empleado) {
        return switch (empleado) {
            case Desarrollador d -> new DatosRol("DESARROLLADOR", d.getLenguajePrincipal(), null);
            case Gerente g -> new DatosRol("GERENTE", null, g.getPresupuestoMensual());
        };
    }

    @Override
    public void insertar(Empleado empleado) {
        var datos = extraerDatosRol(empleado);

        var sql = """
                INSERT INTO empleados
                    (nombre, edad, salario, rol, lenguaje_principal, presupuesto_mensual,
                     calificacion1, calificacion2, calificacion3)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING id
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, empleado.getNombre());
            sentencia.setInt(2, empleado.getEdad());
            sentencia.setDouble(3, empleado.getSalario());
            sentencia.setString(4, datos.rol());
            sentencia.setString(5, datos.lenguajePrincipal());

            if (datos.presupuestoMensual() == null) {
                sentencia.setNull(6, Types.NUMERIC);
            } else {
                sentencia.setDouble(6, datos.presupuestoMensual());
            }

            sentencia.setDouble(7, empleado.getCalificacion1());
            sentencia.setDouble(8, empleado.getCalificacion2());
            sentencia.setDouble(9, empleado.getCalificacion3());

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    empleado.setId(resultado.getInt("id"));
                }
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al insertar el empleado.", excepcion);
        }
    }

    @Override
    public List<Empleado> listar() {
        var sql = """
                SELECT id, nombre, edad, salario, rol, lenguaje_principal, presupuesto_mensual,
                       calificacion1, calificacion2, calificacion3
                FROM empleados
                ORDER BY id
                """;

        var empleados = new ArrayList<Empleado>();

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                empleados.add(mapearEmpleado(resultado));
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al listar los empleados.", excepcion);
        }

        return empleados;
    }

    @Override
    public void actualizar(Empleado empleado) {
        var datos = extraerDatosRol(empleado);

        var sql = """
                UPDATE empleados
                SET nombre = ?, edad = ?, salario = ?, rol = ?, lenguaje_principal = ?,
                    presupuesto_mensual = ?, calificacion1 = ?, calificacion2 = ?, calificacion3 = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, empleado.getNombre());
            sentencia.setInt(2, empleado.getEdad());
            sentencia.setDouble(3, empleado.getSalario());
            sentencia.setString(4, datos.rol());
            sentencia.setString(5, datos.lenguajePrincipal());

            if (datos.presupuestoMensual() == null) {
                sentencia.setNull(6, Types.NUMERIC);
            } else {
                sentencia.setDouble(6, datos.presupuestoMensual());
            }

            sentencia.setDouble(7, empleado.getCalificacion1());
            sentencia.setDouble(8, empleado.getCalificacion2());
            sentencia.setDouble(9, empleado.getCalificacion3());
            sentencia.setInt(10, empleado.getId());

            var filasActualizadas = sentencia.executeUpdate();

            if (filasActualizadas == 0) {
                throw new NoSuchElementException(
                        "No existe un empleado con id " + empleado.getId());
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al actualizar el empleado.", excepcion);
        }
    }

    @Override
    public void eliminar(int id) {
        var sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            var filasEliminadas = sentencia.executeUpdate();

            if (filasEliminadas == 0) {
                throw new NoSuchElementException("No existe un empleado con id " + id);
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al eliminar el empleado.", excepcion);
        }
    }

    @Override
    public int eliminarPorPuntajeMinimo(double puntajeMinimo) {
        var sql = """
                DELETE FROM empleados
                WHERE (calificacion1 + calificacion2 + calificacion3) / 3 < ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setDouble(1, puntajeMinimo);

            return sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw new RuntimeException(
                    "Error al filtrar empleados por puntaje mínimo.", excepcion);
        }
    }

    /*
     * Task 4 - Análisis moderno:
     * Esta consulta agrega y clasifica los datos directamente en SQL
     * (AVG + CASE) y cada fila se mapea a un DesempeñoReport, un record
     * inmutable. Frente a un POJO tradicional de Java 8 -que exigiría
     * escribir a mano constructor, getters y toString solo para
     * transportar estos tres valores de lectura-, el record expresa la
     * misma información en una sola línea, sin lógica mutable que
     * proteger y con equals/hashCode/toString generados por el
     * compilador. Esto reduce el código repetitivo del mapeo
     * ResultSet -> objeto y hace explícito que el resultado de la
     * consulta es un dato de solo lectura, no una entidad editable.
     */
    @Override
    public List<DesempeñoReport> generarReporteFinal() {
        var sql = """
                SELECT id,
                       (calificacion1 + calificacion2 + calificacion3) / 3 AS promedio,
                       CASE
                           WHEN (calificacion1 + calificacion2 + calificacion3) / 3 >= 80
                               THEN 'PROMOVIDO'
                           ELSE 'NO PROMOVIDO'
                       END AS feedback
                FROM empleados
                ORDER BY id
                """;

        var reportes = new ArrayList<DesempeñoReport>();

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                reportes.add(new DesempeñoReport(
                        resultado.getInt("id"),
                        resultado.getDouble("promedio"),
                        resultado.getString("feedback")));
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al generar el reporte final.", excepcion);
        }

        return reportes;
    }

    private Empleado mapearEmpleado(ResultSet resultado) throws SQLException {
        var id = resultado.getInt("id");
        var nombre = resultado.getString("nombre");
        var edad = resultado.getInt("edad");
        var salario = resultado.getDouble("salario");
        var rol = resultado.getString("rol");
        var calificacion1 = resultado.getDouble("calificacion1");
        var calificacion2 = resultado.getDouble("calificacion2");
        var calificacion3 = resultado.getDouble("calificacion3");

        return switch (rol) {
            case "DESARROLLADOR" -> new Desarrollador(
                    id, nombre, edad, salario, resultado.getString("lenguaje_principal"),
                    calificacion1, calificacion2, calificacion3);
            case "GERENTE" -> new Gerente(
                    id, nombre, edad, salario, resultado.getDouble("presupuesto_mensual"),
                    calificacion1, calificacion2, calificacion3);
            default -> throw new IllegalStateException(
                    "Rol desconocido en la base de datos: " + rol);
        };
    }
}
