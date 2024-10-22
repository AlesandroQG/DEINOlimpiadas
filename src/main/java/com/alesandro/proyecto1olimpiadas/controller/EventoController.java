package com.alesandro.proyecto1olimpiadas.controller;

import com.alesandro.proyecto1olimpiadas.model.Evento;

/**
 * Clase que controla los eventos de la ventana evento
 */
public class EventoController {
    private Evento evento;

    /**
     * Constructor al que se pasa el evento a editar
     *
     * @param evento a editar
     */
    public EventoController(Evento evento) {
        this.evento = evento;
    }

    /**
     * Constructor vacío para añadir un nuevo evento
     */
    public EventoController() {
        this.evento = null;
    }

}
