package com.riwi.talent.controller;

import com.riwi.talent.model.DesempeñoReport;
import com.riwi.talent.model.Desarrollador;
import com.riwi.talent.model.Empleado;
import com.riwi.talent.model.EmpleadoDAO;
import com.riwi.talent.model.EmpleadoDAOImpl;
import com.riwi.talent.model.Gerente;

import java.util.List;

/**
 * Mediador entre la Vista y el Modelo. No conoce Scanner ni System.in:
 * recibe datos ya leídos por la vista, coordina la operación con el
 * EmpleadoDAO y devuelve resultados listos para mostrar.
 */
public class EmpleadoController {

    private static final double PROMEDIO_PARA_PROMOCION = 80.0;

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    public void registrarDesarrollador(String nombre, int edad, double salario,
            String lenguajePrincipal, double calificacion1, double calificacion2,
            double calificacion3) {

        empleadoDAO.insertar(new Desarrollador(
                nombre, edad, salario, lenguajePrincipal,
                calificacion1, calificacion2, calificacion3));
    }

    public void registrarGerente(String nombre, int edad, double salario,
            double presupuestoMensual, double calificacion1, double calificacion2,
            double calificacion3) {

        empleadoDAO.insertar(new Gerente(
                nombre, edad, salario, presupuestoMensual,
                calificacion1, calificacion2, calificacion3));
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoDAO.listar();
    }

    public void actualizarDesarrollador(int id, String nombre, int edad, double salario,
            String lenguajePrincipal, double calificacion1, double calificacion2,
            double calificacion3) {

        empleadoDAO.actualizar(new Desarrollador(
                id, nombre, edad, salario, lenguajePrincipal,
                calificacion1, calificacion2, calificacion3));
    }

    public void actualizarGerente(int id, String nombre, int edad, double salario,
            double presupuestoMensual, double calificacion1, double calificacion2,
            double calificacion3) {

        empleadoDAO.actualizar(new Gerente(
                id, nombre, edad, salario, presupuestoMensual,
                calificacion1, calificacion2, calificacion3));
    }

    public void eliminarEmpleado(int id) {
        empleadoDAO.eliminar(id);
    }

    public int filtrarPorPuntajeMinimo(double puntajeMinimo) {
        return empleadoDAO.eliminarPorPuntajeMinimo(puntajeMinimo);
    }

    public List<DesempeñoReport> generarReporteFinal() {
        return empleadoDAO.generarReporteFinal();
    }

    public String obtenerCategoriaSalarial(Empleado empleado) {
        var salario = empleado.getSalario();

        if (salario < 2_000_000.0) {
            return "JUNIOR";
        } else if (salario < 4_000_000.0) {
            return "SEMISENIOR";
        } else if (salario < 7_000_000.0) {
            return "SENIOR";
        } else {
            return "LÍDER";
        }
    }

    public String obtenerEstadoPromocion(Empleado empleado) {
        return empleado.getPromedioDesempeno() >= PROMEDIO_PARA_PROMOCION
                ? "PROMOVIDO"
                : "NO PROMOVIDO";
    }
}
