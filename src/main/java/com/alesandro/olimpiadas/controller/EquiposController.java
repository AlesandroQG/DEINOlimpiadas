package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.model.Equipo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Clase que controla los eventos de la ventana equipos
 */
public class EquiposController {
    @FXML // fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader

    @FXML // fx:id="cbEquipo"
    private ComboBox<Equipo> cbEquipo; // Value injected by FXMLLoader

    @FXML // fx:id="txtIniciales"
    private TextField txtIniciales; // Value injected by FXMLLoader

    @FXML // fx:id="txtNombre"
    private TextField txtNombre; // Value injected by FXMLLoader

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void eliminar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }
}
