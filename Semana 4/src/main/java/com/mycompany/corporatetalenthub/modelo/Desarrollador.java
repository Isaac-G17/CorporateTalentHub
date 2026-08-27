/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 *
 * @author Cohorte 5
 */
public final class Desarrollador extends Empleado implements Promocionable {
    
    private final String lenguajePrincipal;
    
    public Desarrollador(int id, String nombre, byte edad, double salario,
            double [] calificaciones, String lenguajePrincipal){
        
        super(id,nombre,edad,salario,calificaciones);
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
