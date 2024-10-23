package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.dao.DaoOlimpiada;
import com.alesandro.olimpiadas.model.Olimpiada;
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
 * Clase que controla los eventos de la ventana olimpiadas
 */
public class OlimpiadasController implements Initializable {
    private Olimpiada olimpiada;
    private Olimpiada crear;

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
        this.olimpiada = null;
        crear = new Olimpiada();
        crear.setId_olimpiada(0);
        crear.setNombre(resources.getString("cb.new"));
        cargarOlimpiadas();
        // Listener ComboBox
        cbOlimpiada.getSelectionModel().selectedItemProperty().addListener(this::cambioOlimpiada);
    }

    /**
     * Función que carga las olimpiadas de la base de datos al ComboBox
     */
    public void cargarOlimpiadas() {
        cbOlimpiada.getItems().clear();
        cbOlimpiada.getItems().add(crear);
        ObservableList<Olimpiada> olimpiadas = DaoOlimpiada.cargarListado();
        cbOlimpiada.getItems().addAll(olimpiadas);
        cbOlimpiada.getSelectionModel().select(0);
    }

    /**
     * Listener del cambio del ComboBox
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void cambioOlimpiada(ObservableValue<? extends Olimpiada> observable, Olimpiada oldValue, Olimpiada newValue) {
        if (newValue != null) {
            btnEliminar.setDisable(true);
            if (newValue.equals(crear)) {
                olimpiada = null;
                txtNombre.setText(null);
                txtAnio.setText(null);
                rbInvierno.setSelected(true);
                rbVerano.setSelected(false);
                txtCiudad.setText(null);
            } else {
                olimpiada = newValue;
                txtNombre.setText(olimpiada.getNombre());
                txtAnio.setText(olimpiada.getAnio() + "");
                if (olimpiada.getTemporada().equals("Winter")) {
                    rbInvierno.setSelected(true);
                    rbVerano.setSelected(false);
                } else {
                    rbVerano.setSelected(true);
                    rbInvierno.setSelected(false);
                }
                txtCiudad.setText(olimpiada.getCiudad());
                if (DaoOlimpiada.esEliminable(olimpiada)) {
                    btnEliminar.setDisable(false);
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
     * Función que se ejecuta cuando se pulsa el botón "Eliminar". Elimina una olimpiada
     *
     * @param event
     */
    @FXML
    void eliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(txtNombre.getScene().getWindow());
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estás seguro que quieres eliminar esta olimpiada?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (DaoOlimpiada.eliminar(olimpiada)) {
                confirmacion("Olimpiada eliminada correctamente");
                cargarOlimpiadas();
            } else {
                alerta("Ha habido un error eliminando esa olimpiada. Por favor vuelva a intentarlo");
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
        String error = validar();
        if (!error.isEmpty()) {
            alerta(error);
        } else {
            Olimpiada nuevo = new Olimpiada();
            nuevo.setNombre(txtNombre.getText());
            nuevo.setAnio(Integer.parseInt(txtAnio.getText()));
            if (rbInvierno.isSelected()) {
                nuevo.setTemporada(nuevo.getSeasonCategory("Winter"));
            } else {
                nuevo.setTemporada(nuevo.getSeasonCategory("Summer"));
            }
            nuevo.setCiudad(txtCiudad.getText());
            if (this.olimpiada == null) {
                int id = DaoOlimpiada.insertar(nuevo);
                if (id == -1) {
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                } else {
                    confirmacion("Olimpiada creada correctamente");
                    cargarOlimpiadas();
                }
            } else {
                if (DaoOlimpiada.modificar(this.olimpiada, nuevo)) {
                    confirmacion("Olimpiada actualizada correctamente");
                    cargarOlimpiadas();
                } else {
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                }
            }
        }
    }

    /**
     * Válida los datos del formulario
     *
     * @return string con posibles errores
     */
    public String validar() {
        String error = "";
        if (txtNombre.getText().isEmpty()) {
            error = "El campo nombre no puede estar vacío\n";
        }
        if (txtAnio.getText().isEmpty()) {
            error += "El campo año no puede estar vacío\n";
        } else {
            try {
                Integer.parseInt(txtAnio.getText());
            } catch (NumberFormatException e) {
                error += "El campo año tiene que ser numérico\n";
            }
        }
        if (txtCiudad.getText().isEmpty()) {
            error += "El campo ciudad no puede estar vacío\n";
        }
        return error;
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
