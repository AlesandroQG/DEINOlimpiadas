package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.dao.DaoEquipo;
import com.alesandro.olimpiadas.model.Equipo;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana equipos
 */
public class EquiposController implements Initializable {
    private Equipo equipo;
    private Equipo crear;

    @FXML // fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader

    @FXML // fx:id="cbEquipo"
    private ComboBox<Equipo> cbEquipo; // Value injected by FXMLLoader

    @FXML // fx:id="txtIniciales"
    private TextField txtIniciales; // Value injected by FXMLLoader

    @FXML // fx:id="txtNombre"
    private TextField txtNombre; // Value injected by FXMLLoader

    @FXML // fx:id="lblDelete"
    private Label lblDelete; // Value injected by FXMLLoader

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
        this.equipo = null;
        crear = new Equipo();
        crear.setId_equipo(0);
        crear.setNombre(resources.getString("cb.new"));
        cargarEquipos();
        // Listener ComboBox
        cbEquipo.getSelectionModel().selectedItemProperty().addListener(this::cambioEquipo);
    }

    /**
     * Función que carga los equipos de la base de datos al ComboBox
     */
    public void cargarEquipos() {
        cbEquipo.getItems().clear();
        cbEquipo.getItems().add(crear);
        ObservableList<Equipo> equipos = DaoEquipo.cargarListado();
        cbEquipo.getItems().addAll(equipos);
        cbEquipo.getSelectionModel().select(0);
    }

    /**
     * Listener del cambio del ComboBox
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void cambioEquipo(ObservableValue<? extends Equipo> observable, Equipo oldValue, Equipo newValue) {
        if (newValue != null) {
            btnEliminar.setDisable(true);
            lblDelete.setVisible(false);
            if (newValue.equals(crear)) {
                equipo = null;
                txtNombre.setText(null);
                txtIniciales.setText(null);
            } else {
                equipo = newValue;
                txtNombre.setText(equipo.getNombre());
                txtIniciales.setText(equipo.getIniciales());
                if (DaoEquipo.esEliminable(equipo)) {
                    btnEliminar.setDisable(false);
                } else {
                    lblDelete.setVisible(true);
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtNombre.getScene().getWindow();
        stage.close();
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Eliminar". Elimina un equipo
     *
     * @param event
     */
    @FXML
    void eliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(txtNombre.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle(resources.getString("window.confirm"));
        alert.setContentText(resources.getString("delete.teams.prompt"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (DaoEquipo.eliminar(equipo)) {
                confirmacion(resources.getString("delete.teams.success"));
                cargarEquipos();
            } else {
                alerta(resources.getString("delete.teams.fail"));
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Guardar". Válida y procesa los datos
     *
     * @param event
     */
    @FXML
    void guardar(ActionEvent event) {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error = resources.getString("validate.teams.name") + "\n";
        }
        if (txtIniciales.getText().isEmpty()) {
            error +=  resources.getString("validate.teams.noc") + "\n";
        } else {
            if (txtIniciales.getText().length() > 3) {
                error +=  resources.getString("validate.teams.noc.num") +  "\n";
            }
        }
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Equipo nuevo = new Equipo();
            nuevo.setNombre(txtNombre.getText());
            nuevo.setIniciales(txtIniciales.getText());
            if (this.equipo == null) {
                int id = DaoEquipo.insertar(nuevo);
                if (id == -1) {
                    alerta(resources.getString("save.fail"));
                } else {
                    confirmacion(resources.getString("save.teams"));
                    cargarEquipos();
                }
            } else {
                if (DaoEquipo.modificar(equipo, nuevo)) {
                    confirmacion(resources.getString("update.teams"));
                    cargarEquipos();
                } else {
                    alerta(resources.getString("save.fail"));
                }
            }
        }
    }

    /**
     * Función que muestra un mensaje de alerta al usuario
     *
     * @param texto contenido de la alerta
     */
    public void alerta(String texto) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);
        alerta.setTitle("Error");
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
