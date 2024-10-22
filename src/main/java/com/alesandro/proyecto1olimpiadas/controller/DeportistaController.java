package com.alesandro.proyecto1olimpiadas.controller;

import com.alesandro.proyecto1olimpiadas.model.Deportista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana deportista
 */
public class DeportistaController implements Initializable {
    private Deportista deportista;

    @FXML // fx:id="foto"
    private ImageView foto; // Value injected by FXMLLoader

    @FXML // fx:id="rbFemale"
    private RadioButton rbFemale; // Value injected by FXMLLoader

    @FXML // fx:id="rbMale"
    private RadioButton rbMale; // Value injected by FXMLLoader

    @FXML // fx:id="tgSexo"
    private ToggleGroup tgSexo; // Value injected by FXMLLoader

    @FXML // fx:id="txtAltura"
    private TextField txtAltura; // Value injected by FXMLLoader

    @FXML // fx:id="txtNombre"
    private TextField txtNombre; // Value injected by FXMLLoader

    @FXML // fx:id="txtPeso"
    private TextField txtPeso; // Value injected by FXMLLoader

    @FXML
    private ResourceBundle resources; // ResourceBundle injected automatically by FXML loader

    /**
     * Constructor al que se pasa el deportista a editar
     *
     * @param deportista a editar
     */
    public DeportistaController(Deportista deportista) {
        this.deportista = deportista;
    }

    /**
     * Constructor vacío para añadir un nuevo deportista
     */
    public DeportistaController() {
        this.deportista = null;
    }

    /**
     * Función que se ejecuta cuando se inicia la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        //
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

    @FXML
    void seleccionImagen(ActionEvent event) {

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
