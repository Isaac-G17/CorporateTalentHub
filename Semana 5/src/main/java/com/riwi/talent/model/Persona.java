package com.riwi.talent.model;

/**
 * Las Sealed Classes ofrecen mayor seguridad en el diseño de APIs porque
 * permiten controlar explícitamente qué clases pueden heredar de una clase
 * base. A diferencia de la herencia abierta, donde cualquier clase puede
 * extenderla y modificar su comportamiento, una sealed class limita las
 * implementaciones a las clases definidas mediante permits. Esto hace que
 * la jerarquía sea predecible, facilita el mantenimiento y permite al
 * compilador detectar casos no contemplados, especialmente cuando se
 * usan switch y pattern matching (ver EmpleadoDAOImpl y ConsoleView).
 */
public sealed abstract class Persona permits Empleado, ConsultorExterno {

    private int id;
    private final String nombre;
    private final int edad;

    protected Persona(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }

    protected Persona(String nombre, int edad) {
        this(0, nombre, edad);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }
}
