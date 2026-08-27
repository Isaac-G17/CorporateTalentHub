/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 *
 * @author Cohorte 5
 */
public interface Promocionable {
    
    
    double calcularBonoAscenso ();
    
    default void registrarLog(String nombreEmpleado,double bono){
        System.out.printf("[LOG PROMOCIÓN] %s recibirá un bono de %.2f%n",
                nombreEmpleado,
                bono);
    }
    
}
