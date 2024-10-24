package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.dao.DaoDeportista;
import com.alesandro.olimpiadas.dao.DaoEquipo;
import com.alesandro.olimpiadas.dao.DaoEvento;
import com.alesandro.olimpiadas.dao.DaoParticipacion;
import com.alesandro.olimpiadas.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana participación
 */
public class ParticipacionController implements Initializable {
    private Participacion participacion;

    @FXML // fx:id="lstDeportista"
    private ListView<Deportista> lstDeportista; // Value injected by FXMLLoader

    @FXML // fx:id="lstEquipo"
    private ListView<Equipo> lstEquipo; // Value injected by FXMLLoader

    @FXML // fx:id="lstEvento"
    private ListView<Evento> lstEvento; // Value injected by FXMLLoader

    @FXML // fx:id="txtEdad"
    private TextField txtEdad; // Value injected by FXMLLoader

    @FXML // fx:id="txtMedalla"
    private TextField txtMedalla; // Value injected by FXMLLoader

    @FXML
    private ResourceBundle resources; // ResourceBundle injected automatically by FXML loader

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
     * Función que se ejecuta cuando se inicia la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        cargarListas();
        if (this.participacion != null) {
            lstDeportista.getSelectionModel().select(participacion.getDeportista());
            lstDeportista.setDisable(true);
            lstEvento.getSelectionModel().select(participacion.getEvento());
            lstEvento.setDisable(true);
            lstEquipo.getSelectionModel().select(participacion.getEquipo());
            txtEdad.setText(participacion.getEdad() + "");
            txtMedalla.setText(participacion.getMedalla());
        }
    }

    /**
     * Función que carga las listas
     */
    public void cargarListas() {
        ObservableList<Deportista> deportistas = DaoDeportista.cargarListado();
        lstDeportista.getItems().addAll(deportistas);
        ObservableList<Evento> eventos = DaoEvento.cargarListado();
        lstEvento.getItems().addAll(eventos);
        ObservableList<Equipo> equipos = DaoEquipo.cargarListado();
        lstEquipo.getItems().addAll(equipos);
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Cancelar". Cierra la ventana
     *
     * @param event
     */
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)txtEdad.getScene().getWindow();
        stage.close();
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
            Participacion nuevo = new Participacion();
            nuevo.setDeportista(lstDeportista.getSelectionModel().getSelectedItem());
            nuevo.setEvento(lstEvento.getSelectionModel().getSelectedItem());
            nuevo.setEquipo(lstEquipo.getSelectionModel().getSelectedItem());
            nuevo.setEdad(Integer.parseInt(txtEdad.getText()));
            nuevo.setMedalla(txtMedalla.getText());
            if (this.participacion == null) {
                if (DaoParticipacion.insertar(nuevo)) {
                    confirmacion("Participación creada correctamente");
                    Stage stage = (Stage)txtEdad.getScene().getWindow();
                    stage.close();
                } else {
                    alerta("Ha habido un error almacenando los datos. Por favor vuelva a intentarlo");
                }
            } else {
                if (DaoParticipacion.modificar(participacion, nuevo)) {
                    confirmacion("Participación actualizada correctamente");
                    Stage stage = (Stage)txtEdad.getScene().getWindow();
                    stage.close();
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
        if (lstDeportista.getSelectionModel().getSelectedItems() == null) {
            error = "Un deportista de la lista tiene que estar seleccionada\n";
        }
        if (lstEvento.getSelectionModel().getSelectedItems() == null) {
            error += "Un evento de la lista tiene que estar seleccionada\n";
        }
        if (lstEquipo.getSelectionModel().getSelectedItems() == null) {
            error += "Un equipo de la lista tiene que estar seleccionada\n";
        }
        if (txtEdad.getText().isEmpty()) {
            error += "El campo edad no puede estar vacío\n";
        } else {
            try {
                Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException e) {
                error += "El campo edad tiene que ser numérico\n";
            }
        }
        if (txtMedalla.getText().isEmpty()) {
            error += "El campo medalla no puede estar vacío\n";
        } else {
            if (txtMedalla.getText().length() > 6) {
                error += "El campo iniciales no puede ser mayor que 6 carácteres\n";
            }
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
