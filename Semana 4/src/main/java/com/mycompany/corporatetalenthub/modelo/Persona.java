/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.corporatetalenthub.modelo;

/**
 * Las Sealed Classes ofrecen mayor seguridad en el diseño de APIs porque 
 * permiten controlar explícitamente qué clases pueden heredar de una clase 
 * base. A diferencia de la herencia abierta, donde cualquier clase 
 * puede extenderla y modificar su comportamiento, una sealed class limita 
 * las implementaciones a las clases definidas mediante permits. Esto hace 
 * que la jerarquía sea predecible, facilita el mantenimiento y permite al
 * compilador detectar casos no contemplados, especialmente cuando 
 * se utilizan switch y pattern matching.
 * 
 */
public sealed abstract class Persona permits Empleado, ConsultorExterno {
    
    private final int id;
    private final String nombre;
    private final byte edad;

    protected Persona(int id, String nombre, byte edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
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
    
    
    
    
}
