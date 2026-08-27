/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 *
 * @author Cohorte 5
 */
public final class ConsultorExterno extends Persona{
    
    private final String especialidad;
    
    public ConsultorExterno(int id, String nombre, byte edad,
            String especialidad){
        
        super(id,nombre,edad);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
   
    
}
