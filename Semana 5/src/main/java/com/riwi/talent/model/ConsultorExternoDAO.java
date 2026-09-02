package com.riwi.talent.model;

import java.util.List;

public interface ConsultorExternoDAO {

    void insertar(ConsultorExterno consultor);

    List<ConsultorExterno> listar();

    void actualizar(ConsultorExterno consultor);

    void eliminar(int id);
}
