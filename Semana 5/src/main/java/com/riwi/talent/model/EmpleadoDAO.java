package com.riwi.talent.model;

import java.util.List;

/**
 * Contrato de acceso a datos para la jerarquía Empleado
 * (Desarrollador / Gerente).
 */
public interface EmpleadoDAO {

    void insertar(Empleado empleado);

    List<Empleado> listar();

    void actualizar(Empleado empleado);

    void eliminar(int id);

    int eliminarPorPuntajeMinimo(double puntajeMinimo);

    List<DesempeñoReport> generarReporteFinal();
}
