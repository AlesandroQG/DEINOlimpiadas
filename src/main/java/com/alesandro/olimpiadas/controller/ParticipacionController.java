package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.model.Participacion;
import javafx.scene.control.Alert;

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
