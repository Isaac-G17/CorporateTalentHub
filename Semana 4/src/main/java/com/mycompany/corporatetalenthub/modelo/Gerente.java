/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 *
 * @author Cohorte 5
 */
public final class Gerente extends Empleado implements Promocionable{
    
    private final double presupuestoMensual;
    
    public Gerente(int id,String nombre,byte edad,double salario,
            double [] calificaciones, double presupuestoMensual){
        
        super(id,nombre,edad,salario,calificaciones);
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
