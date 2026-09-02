package com.riwi.talent.controller;

import com.riwi.talent.model.ConsultorExterno;
import com.riwi.talent.model.ConsultorExternoDAO;
import com.riwi.talent.model.ConsultorExternoDAOImpl;

import java.util.List;

public class ConsultorExternoController {

    private final ConsultorExternoDAO consultorDAO;

    public ConsultorExternoController() {
        this.consultorDAO = new ConsultorExternoDAOImpl();
    }

    public void registrarConsultor(String nombre, int edad, String especialidad) {
        consultorDAO.insertar(new ConsultorExterno(nombre, edad, especialidad));
    }

    public List<ConsultorExterno> obtenerConsultores() {
        return consultorDAO.listar();
    }

    public void actualizarConsultor(int id, String nombre, int edad, String especialidad) {
        consultorDAO.actualizar(new ConsultorExterno(id, nombre, edad, especialidad));
    }

    public void eliminarConsultor(int id) {
        consultorDAO.eliminar(id);
    }
}
