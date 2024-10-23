package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.dao.DaoDeporte;
import com.alesandro.olimpiadas.model.Deporte;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana deportes
 */
public class DeportesController implements Initializable {
    @FXML // fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader

    @FXML // fx:id="cbDeporte"
    private ComboBox<Deporte> cbDeporte; // Value injected by FXMLLoader

    @FXML // fx:id="txtNombre"
    private TextField txtNombre; // Value injected by FXMLLoader

    @FXML
    private ResourceBundle resources; // ResourceBundle injected automatically by FXML loader

    /**
     * Función que se ejecuta cuando se inicia la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        Deporte nuevo = new Deporte();
        nuevo.setId_deporte(0);
        nuevo.setNombre(resources.getString("cb.new"));
        cbDeporte.getItems().add(nuevo);
        ObservableList<Deporte> deportes = DaoDeporte.cargarListado();
        cbDeporte.getItems().addAll(deportes);
        cbDeporte.getSelectionModel().select(0);
    }

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
