package com.alesandro.proyecto1olimpiadas.controller;

import com.alesandro.proyecto1olimpiadas.model.Participacion;

/**
 * Clase que controla los eventos de la ventana participación
 */
public class ParticipacionController {
    private Participacion participacion;

    /**
     * Constructor al que se pasa la participación a editar
     *
     * @param participacion a editar
     */
    public ParticipacionController(Participacion participacion) {
        this.participacion = participacion;
    }

    /**
     * Constructor vacío para añadir una nueva participación
     */
    public ParticipacionController() {
        this.participacion = null;
    }

}
