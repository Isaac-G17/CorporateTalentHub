package com.riwi.talent.model;

/**
 * Record inmutable para el reporte de desempeño de fin de mes.
 * Reutilizado de semanas anteriores para transportar el resultado de la
 * consulta SELECT consolidada del reporte final (ver
 * EmpleadoDAOImpl.generarReporteFinal()).
 */
public record DesempeñoReport(int idEmpleado, double promedio, String feedback) {
}
