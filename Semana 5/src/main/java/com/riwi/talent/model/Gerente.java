package com.riwi.talent.model;

public final class Gerente extends Empleado implements Promocionable {

    private final double presupuestoMensual;

    public Gerente(int id, String nombre, int edad, double salario, double presupuestoMensual,
            double calificacion1, double calificacion2, double calificacion3) {

        super(id, nombre, edad, salario, calificacion1, calificacion2, calificacion3);
        this.presupuestoMensual = presupuestoMensual;
    }

    public Gerente(String nombre, int edad, double salario, double presupuestoMensual,
            double calificacion1, double calificacion2, double calificacion3) {

        super(nombre, edad, salario, calificacion1, calificacion2, calificacion3);
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    @Override
    public double calcularBonoAscenso() {
        return (getSalario() * 0.10) + (presupuestoMensual * 0.02);
    }
}
