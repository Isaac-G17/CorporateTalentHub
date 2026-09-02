package com.riwi.talent.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ConsultorExternoDAOImpl implements ConsultorExternoDAO {

    @Override
    public void insertar(ConsultorExterno consultor) {
        var sql = """
                INSERT INTO consultores_externos (nombre, edad, especialidad)
                VALUES (?, ?, ?)
                RETURNING id
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, consultor.getNombre());
            sentencia.setInt(2, consultor.getEdad());
            sentencia.setString(3, consultor.getEspecialidad());

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    consultor.setId(resultado.getInt("id"));
                }
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al insertar el consultor externo.", excepcion);
        }
    }

    @Override
    public List<ConsultorExterno> listar() {
        var sql = "SELECT id, nombre, edad, especialidad FROM consultores_externos ORDER BY id";

        var consultores = new ArrayList<ConsultorExterno>();

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                consultores.add(new ConsultorExterno(
                        resultado.getInt("id"),
                        resultado.getString("nombre"),
                        resultado.getInt("edad"),
                        resultado.getString("especialidad")));
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al listar los consultores externos.", excepcion);
        }

        return consultores;
    }

    @Override
    public void actualizar(ConsultorExterno consultor) {
        var sql = """
                UPDATE consultores_externos
                SET nombre = ?, edad = ?, especialidad = ?
                WHERE id = ?
                """;

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, consultor.getNombre());
            sentencia.setInt(2, consultor.getEdad());
            sentencia.setString(3, consultor.getEspecialidad());
            sentencia.setInt(4, consultor.getId());

            var filasActualizadas = sentencia.executeUpdate();

            if (filasActualizadas == 0) {
                throw new NoSuchElementException(
                        "No existe un consultor externo con id " + consultor.getId());
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al actualizar el consultor externo.", excepcion);
        }
    }

    @Override
    public void eliminar(int id) {
        var sql = "DELETE FROM consultores_externos WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            var filasEliminadas = sentencia.executeUpdate();

            if (filasEliminadas == 0) {
                throw new NoSuchElementException("No existe un consultor externo con id " + id);
            }
        } catch (SQLException excepcion) {
            throw new RuntimeException("Error al eliminar el consultor externo.", excepcion);
        }
    }
}
