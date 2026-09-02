/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 *
 * @author cohorte5
 */

public sealed abstract class Empleado extends Persona permits Desarrollador, Gerente{

    private final double salario;
    private final double[] calificaciones;
    private double promedioDesempeno;

    protected Empleado(int id, String nombre, byte edad, double salario, double[] calificaciones) {
        
        super(id,nombre,edad);
        this.salario = salario;
        this.calificaciones = calificaciones;
    }

    public double getSalario() {
        return salario;
    }
    
    public double[] getCalificaciones() {
        return calificaciones;
    }

    public double getPromedioDesempeno() {
        return promedioDesempeno;
    }

    public void setPromedioDesempeno(double promedioDesempeno) {
        this.promedioDesempeno = promedioDesempeno;
    }
}