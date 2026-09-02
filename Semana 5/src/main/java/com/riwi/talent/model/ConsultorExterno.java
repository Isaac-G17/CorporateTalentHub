package com.riwi.talent.model;

public final class ConsultorExterno extends Persona {

    private final String especialidad;

    public ConsultorExterno(int id, String nombre, int edad, String especialidad) {
        super(id, nombre, edad);
        this.especialidad = especialidad;
    }

    public ConsultorExterno(String nombre, int edad, String especialidad) {
        super(nombre, edad);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
