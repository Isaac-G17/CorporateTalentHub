/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 *
 * @author cohorte5
 */

public class Empleado {

    private final int id;
    private final String nombre;
    private final byte edad;
    private final double salario;
    private final double[] calificaciones;
    private double promedioDesempeno;

    public Empleado(int id, String nombre, byte edad, double salario,double[] calificaciones) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.salario = salario;
        this.calificaciones = calificaciones;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public byte getEdad() {
        return edad;
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