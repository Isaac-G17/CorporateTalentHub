package com.riwi.talent.model;

/**
 * Empleado también es sealed: solo Desarrollador y Gerente pueden
 * extenderla. Combinado con Persona, forma una jerarquía cerrada de dos
 * niveles que el compilador puede verificar de forma exhaustiva (ver el
 * switch sin rama default en EmpleadoDAOImpl.mapearEmpleado).
 */
public sealed abstract class Empleado extends Persona permits Desarrollador, Gerente {

    private final double salario;
    private final double calificacion1;
    private final double calificacion2;
    private final double calificacion3;

    protected Empleado(int id, String nombre, int edad, double salario,
            double calificacion1, double calificacion2, double calificacion3) {

        super(id, nombre, edad);
        this.salario = salario;
        this.calificacion1 = calificacion1;
        this.calificacion2 = calificacion2;
        this.calificacion3 = calificacion3;
    }

    protected Empleado(String nombre, int edad, double salario,
            double calificacion1, double calificacion2, double calificacion3) {

        super(nombre, edad);
        this.salario = salario;
        this.calificacion1 = calificacion1;
        this.calificacion2 = calificacion2;
        this.calificacion3 = calificacion3;
    }

    public double getSalario() {
        return salario;
    }

    public double getCalificacion1() {
        return calificacion1;
    }

    public double getCalificacion2() {
        return calificacion2;
    }

    public double getCalificacion3() {
        return calificacion3;
    }

    public double getPromedioDesempeno() {
        return (calificacion1 + calificacion2 + calificacion3) / 3;
    }
}
