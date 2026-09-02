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
    /*
    * Desde Java 8, las interfaces pueden incluir métodos default con
    * una implementación concreta.
    *
    * Esto permite agregar nueva funcionalidad a una interfaz existente
    * sin obligar inmediatamente a todas las clases que ya la implementan
    * a definir el nuevo método.
    *
    * En este caso, registrarLog() proporciona un comportamiento común
    * para las operaciones de promoción.
    */
    default void registrarLog(String nombreEmpleado,double bono){
        System.out.printf("[LOG PROMOCIÓN] %s recibirá un bono de %.2f%n",
                nombreEmpleado,
                bono);
    }
    
}
