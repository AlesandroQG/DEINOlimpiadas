package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.model.Evento;
import javafx.scene.control.Alert;

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

    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("ERROR");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    /**
     * Función que muestra un mensaje de confirmación al usuario
     *
     * @param texto contenido del mensaje
     */
    public void confirmacion(String texto) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Info");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

}
