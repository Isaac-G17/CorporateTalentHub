package com.riwi.talent.model;

public final class Desarrollador extends Empleado implements Promocionable {

    private final String lenguajePrincipal;

    public Desarrollador(int id, String nombre, int edad, double salario, String lenguajePrincipal,
            double calificacion1, double calificacion2, double calificacion3) {

        super(id, nombre, edad, salario, calificacion1, calificacion2, calificacion3);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public Desarrollador(String nombre, int edad, double salario, String lenguajePrincipal,
            double calificacion1, double calificacion2, double calificacion3) {

        super(nombre, edad, salario, calificacion1, calificacion2, calificacion3);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }

    @Override
    public double calcularBonoAscenso() {
        return getSalario() * 0.15;
    }
}
