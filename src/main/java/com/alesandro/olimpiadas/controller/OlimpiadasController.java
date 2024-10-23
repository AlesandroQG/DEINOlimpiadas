package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.model.Olimpiada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Clase que controla los eventos de la ventana olimpiadas
 */
public class OlimpiadasController {

    @FXML // fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader

    @FXML // fx:id="cbOlimpiada"
    private ComboBox<Olimpiada> cbOlimpiada; // Value injected by FXMLLoader

    @FXML // fx:id="rbInvierno"
    private RadioButton rbInvierno; // Value injected by FXMLLoader

    @FXML // fx:id="rbVerano"
    private RadioButton rbVerano; // Value injected by FXMLLoader

    @FXML // fx:id="tgTemporada"
    private ToggleGroup tgTemporada; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnio"
    private TextField txtAnio; // Value injected by FXMLLoader

    @FXML // fx:id="txtCiudad"
    private TextField txtCiudad; // Value injected by FXMLLoader

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
