package com.alesandro.olimpiadas.controller;

import com.alesandro.olimpiadas.dao.DaoDeportista;
import com.alesandro.olimpiadas.dao.DaoEvento;
import com.alesandro.olimpiadas.dao.DaoParticipacion;
import com.alesandro.olimpiadas.language.LanguageSwitcher;
import com.alesandro.olimpiadas.model.Deportista;
import com.alesandro.olimpiadas.model.Evento;
import com.alesandro.olimpiadas.model.Participacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase que controla los eventos de la ventana principal
 */
public class MainController implements Initializable {
    @FXML // fx:id="btnEditar"
    private MenuItem btnEditar; // Value injected by FXMLLoader

    @FXML // fx:id="btnEliminar"
    private MenuItem btnEliminar; // Value injected by FXMLLoader

    @FXML // fx:id="cbTabla"
    private ComboBox<String> cbTabla; // Value injected by FXMLLoader

    @FXML // fx:id="filtroNombre"
    private TextField filtroNombre; // Value injected by FXMLLoader

    @FXML // fx:id="langEN"
    private RadioMenuItem langEN; // Value injected by FXMLLoader

    @FXML // fx:id="langES"
    private RadioMenuItem langES; // Value injected by FXMLLoader

    @FXML // fx:id="tabla"
    private TableView tabla; // Value injected by FXMLLoader

    @FXML // fx:id="tgIdioma"
    private ToggleGroup tgIdioma; // Value injected by FXMLLoader

    @FXML
    private ResourceBundle resources; // ResourceBundle injected automatically by FXML loader

    private ObservableList masterData = FXCollections.observableArrayList();
    private ObservableList filteredData = FXCollections.observableArrayList();

    /**
     * Función que se ejecuta cuando se inicia la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resources = resourceBundle;
        // Idioma
        tgIdioma.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            Locale locale;
            if (langES.isSelected()) {
                locale = new Locale("es");
                langES.setSelected(true);
            } else {
                locale = new Locale("en");
                langEN.setSelected(true);
            }
            new LanguageSwitcher((Stage) tabla.getScene().getWindow()).switchLanguage(locale);
        });
        // Event Listener para ComboBox
        cbTabla.getItems().addAll(resources.getString("cb.athletes"),resources.getString("cb.participations"),resources.getString("cb.events"));
        cbTabla.setValue(resources.getString("cb.athletes"));
        cbTabla.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals(resources.getString("cb.athletes"))) {
                cargarDeportistas();
            } else if (newValue.equals(resources.getString("cb.participations"))) {
                cargarParticipaciones();
            } else {
                cargarEventos();
            }
        });
        // Event Listener para celdas de la tabla
        tabla.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            deshabilitarMenus(newValue == null);
        });
        // Context Menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editarItem = new MenuItem(resources.getString("contextmenu.edit"));
        MenuItem borrarItem = new MenuItem(resources.getString("contextmenu.delete"));
        contextMenu.getItems().addAll(editarItem,borrarItem);
        editarItem.setOnAction(this::editar);
        borrarItem.setOnAction(this::eliminar);
        tabla.setRowFactory(tv -> {
            TableRow<Object> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    tabla.getSelectionModel().select(row.getItem());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
        // Event Listener para el filtro
        filtroNombre.setOnKeyTyped(keyEvent -> filtrar());
        // Doble-click para editar
        tabla.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                editar(null);
            }
        });
        // Carga inicial
        cargarDeportistas();
    }

    /**
     * Función que filtra la tabla por nombre
     */
    public void filtrar() {
        String valor = filtroNombre.getText();
        if (valor != null) {
            String item = cbTabla.getSelectionModel().getSelectedItem();
            if (item.equals(resources.getString("cb.athletes"))) {
                // Deportistas
                if (valor.isEmpty()) {
                    tabla.setItems(masterData);
                } else {
                    filteredData.clear();
                    for (Object obj : masterData) {
                        Deportista deportista = (Deportista) obj;
                        String nombre = deportista.getNombre();
                        nombre = nombre.toLowerCase();
                        if (nombre.contains(valor)) {
                            filteredData.add(deportista);
                        }
                    }
                    tabla.setItems(filteredData);
                }
            } else {
                // Eventos
                if (valor.isEmpty()) {
                    tabla.setItems(masterData);
                } else {
                    filteredData.clear();
                    for (Object obj : masterData) {
                        Evento evento = (Evento) obj;
                        String nombre = evento.getNombre();
                        nombre = nombre.toLowerCase();
                        if (nombre.contains(valor)) {
                            filteredData.add(evento);
                        }
                    }
                    tabla.setItems(filteredData);
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el botón "Añadir". Abre una ventana para añadir objetos de la tabla seleccionada
     *
     * @param event
     */
    @FXML
    void aniadir(ActionEvent event) {
        String item = cbTabla.getSelectionModel().getSelectedItem();
        if (item.equals(resources.getString("cb.athletes"))) {
            // Deportista
            try {
                Window ventana = tabla.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Deportista.fxml"),resources);
                DeportistaController controlador = new DeportistaController();
                fxmlLoader.setController(controlador);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
                stage.setTitle("Añadir Deportista - Olimpiadas");
                stage.initOwner(ventana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                cargarDeportistas();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                alerta("Error abriendo ventana, por favor inténtelo de nuevo");
            }
        } else if (item.equals(resources.getString("cb.participations"))) {
            // Participación
            try {
                Window ventana = tabla.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Participacion.fxml"),resources);
                ParticipacionController controlador = new ParticipacionController();
                fxmlLoader.setController(controlador);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
                stage.setTitle("Añadir Participación - Olimpiadas");
                stage.initOwner(ventana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                cargarParticipaciones();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                alerta("Error abriendo ventana, por favor inténtelo de nuevo");
            }
        } else {
            // Evento
            try {
                Window ventana = tabla.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Evento.fxml"),resources);
                EventoController controlador = new EventoController();
                fxmlLoader.setController(controlador);
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
                stage.setTitle("Añadir Evento - Olimpiadas");
                stage.initOwner(ventana);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                cargarEventos();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                alerta("Error abriendo ventana, por favor inténtelo de nuevo");
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el menu item "Deportes". Abre la ventana de deportes
     *
     * @param event
     */
    @FXML
    void deportes(ActionEvent event) {
        try {
            Window ventana = tabla.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Deportes.fxml"),resources);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
            stage.setTitle("Deportes - Olimpiadas");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el menu item "Editar...". Abre la ventana para la edición del objeto
     *
     * @param event
     */
    @FXML
    void editar(ActionEvent event) {
        Object seleccion = tabla.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            alerta("Selecciona un objeto a eliminar");
        } else {
            String item = cbTabla.getSelectionModel().getSelectedItem();
            if (item.equals(resources.getString("cb.athletes"))) {
                // Deportista
                Deportista deportista = (Deportista) seleccion;
                try {
                    Window ventana = tabla.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Deportista.fxml"),resources);
                    DeportistaController controlador = new DeportistaController(deportista);
                    fxmlLoader.setController(controlador);
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
                    stage.setTitle("Editar Deportista - Olimpiadas");
                    stage.initOwner(ventana);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                    cargarDeportistas();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    alerta("Error abriendo ventana, por favor inténtelo de nuevo");
                }
            } else if (item.equals(resources.getString("cb.participations"))) {
                // Participación
                Participacion participacion = (Participacion) seleccion;
                try {
                    Window ventana = tabla.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Participacion.fxml"),resources);
                    ParticipacionController controlador = new ParticipacionController(participacion);
                    fxmlLoader.setController(controlador);
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
                    stage.setTitle("Editar Participación - Olimpiadas");
                    stage.initOwner(ventana);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                    cargarParticipaciones();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    alerta("Error abriendo ventana, por favor inténtelo de nuevo");
                }
            } else {
                // Evento
                Evento evento = (Evento) seleccion;
                try {
                    Window ventana = tabla.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Evento.fxml"),resources);
                    EventoController controlador = new EventoController(evento);
                    fxmlLoader.setController(controlador);
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
                    stage.setTitle("Editar Evento - Olimpiadas");
                    stage.initOwner(ventana);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                    cargarEventos();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    alerta("Error abriendo ventana, por favor inténtelo de nuevo");
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el menu item "Eliminar...". Elimina el objeto seleccionado
     *
     * @param event
     */
    @FXML
    void eliminar(ActionEvent event) {
        Object seleccion = tabla.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            alerta("Selecciona un objeto a eliminar");
        } else {
            String item = cbTabla.getSelectionModel().getSelectedItem();
            if (item.equals(resources.getString("cb.athletes"))) {
                // Deportista
                Deportista deportista = (Deportista) seleccion;
                if (DaoDeportista.esEliminable(deportista)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(tabla.getScene().getWindow());
                    alert.setHeaderText(null);
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Estás seguro que quieres eliminar ese deportista?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        if (DaoDeportista.eliminar(deportista)) {
                            cargarDeportistas();
                            confirmacion("Deportista eliminado correctamente!");
                        } else {
                            alerta("No se ha podido eliminar ese deportista, por favor inténtelo de nuevo");
                        }
                    }
                } else {
                    alerta("No se puede eliminar ese deportista ya que existen participaciones que dependen en ello");
                }
            } else if (item.equals(resources.getString("cb.participations"))) {
                // Participación
                Participacion participacion = (Participacion) seleccion;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(tabla.getScene().getWindow());
                alert.setHeaderText(null);
                alert.setTitle("Confirmación");
                alert.setContentText("¿Estás seguro que quieres eliminar esa participación?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (DaoParticipacion.eliminar(participacion)) {
                        cargarParticipaciones();
                        confirmacion("Participación eliminada correctamente!");
                    } else {
                        alerta("No se ha podido eliminar esa participación, por favor inténtelo de nuevo");
                    }
                }
            } else {
                // Evento
                Evento evento = (Evento) seleccion;
                if (DaoEvento.esEliminable(evento)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(tabla.getScene().getWindow());
                    alert.setHeaderText(null);
                    alert.setTitle("Confirmación");
                    alert.setContentText("¿Estás seguro que quieres eliminar ese evento?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        if (DaoEvento.eliminar(evento)) {
                            cargarEventos();
                            confirmacion("Evento eliminado correctamente!");
                        } else {
                            alerta("No se ha podido eliminar ese evento, por favor inténtelo de nuevo");
                        }
                    }
                } else {
                    alerta("No se puede eliminar ese evento ya que existen participaciones que dependen en ello");
                }
            }
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el menu item "Equipos". Abre la ventana de equipos
     *
     * @param event
     */
    @FXML
    void equipos(ActionEvent event) {
        try {
            Window ventana = tabla.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Equipos.fxml"),resources);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
            stage.setTitle("Equipos - Olimpiadas");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que se ejecuta cuando se pulsa el menu item "Olimpiadas". Abre la ventana de olimpiadas
     *
     * @param event
     */
    @FXML
    void olimpiadas(ActionEvent event) {
        try {
            Window ventana = tabla.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Olimpiadas.fxml"),resources);
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
            stage.setTitle("Olimpiadas - Olimpiadas");
            stage.initOwner(ventana);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            alerta("Error abriendo ventana, por favor inténtelo de nuevo");
        }
    }

    /**
     * Función que carga en la tabla las columnas de deportista y los deportistas
     */
    private void cargarDeportistas() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        filtroNombre.setEditable(true);
        masterData.clear();
        filteredData.clear();
        tabla.getItems().clear();
        tabla.getColumns().clear();
        // Cargar columnas
        TableColumn<Deportista, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id_deportista"));
        TableColumn<Deportista, String> colNombre = new TableColumn<>(resources.getString("table.athlete.name"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        TableColumn<Deportista, Deportista.SexCategory> colSexo = new TableColumn<>(resources.getString("table.athlete.sex"));
        colSexo.setCellValueFactory(new PropertyValueFactory("sexo"));
        TableColumn<Deportista, Integer> colPeso = new TableColumn<>(resources.getString("table.athlete.weight"));
        colPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        TableColumn<Deportista, Integer> colAltura = new TableColumn<>(resources.getString("table.athlete.height"));
        colAltura.setCellValueFactory(new PropertyValueFactory("altura"));
        tabla.getColumns().addAll(colId,colNombre,colSexo,colPeso,colAltura);
        // Cargar deportistas
        ObservableList<Deportista> deportistas = DaoDeportista.cargarListado();
        masterData.setAll(deportistas);
        tabla.setItems(deportistas);
    }

    /**
     * Función que carga en la tabla las columnas de participaciones y las participaciones
     */
    private void cargarParticipaciones() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        filtroNombre.setEditable(false);
        masterData.clear();
        filteredData.clear();
        tabla.getItems().clear();
        tabla.getColumns().clear();
        // Cargar columnas
        TableColumn<Participacion, String> colDeportista = new TableColumn<>(resources.getString("table.participation.athlete"));
        colDeportista.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getDeportista().getNombre()));
        TableColumn<Participacion, String> colEvento = new TableColumn<>(resources.getString("table.participation.event"));
        colEvento.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getEvento().getNombre()));
        TableColumn<Participacion, String> colEquipo = new TableColumn<>(resources.getString("table.participation.team"));
        colEquipo.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getEquipo().getNombre()));
        TableColumn<Participacion, Integer> colEdad = new TableColumn<>(resources.getString("table.participation.age"));
        colEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        TableColumn<Participacion, String> colMedalla = new TableColumn<>(resources.getString("table.participation.medal"));
        colMedalla.setCellValueFactory(new PropertyValueFactory("medalla"));
        tabla.getColumns().addAll(colDeportista,colEvento,colEquipo,colEdad,colMedalla);
        // Cargar participaciones
        ObservableList<Participacion> participaciones = DaoParticipacion.cargarListado();
        masterData.addAll(participaciones);
        tabla.setItems(participaciones);
    }

    /**
     * Función que carga en la tabla las columnas de eventos y los eventos
     */
    private void cargarEventos() {
        // Vaciar tabla
        tabla.getSelectionModel().clearSelection();
        filtroNombre.setText(null);
        filtroNombre.setEditable(true);
        masterData.clear();
        filteredData.clear();
        tabla.getItems().clear();
        tabla.getColumns().clear();
        // Cargar columnas
        TableColumn<Evento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id_evento"));
        TableColumn<Evento, String> colNombre = new TableColumn<>(resources.getString("table.event.name"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        TableColumn<Evento, String> colOlimpiada = new TableColumn<>(resources.getString("table.event.olympic"));
        colOlimpiada.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getOlimpiada().getNombre()));
        TableColumn<Evento, String> colDeporte = new TableColumn<>(resources.getString("table.event.sport"));
        colDeporte.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(() -> cellData.getValue().getDeporte().getNombre()));
        tabla.getColumns().addAll(colId,colNombre,colOlimpiada,colDeporte);
        // Cargar eventos
        ObservableList<Evento> eventos = DaoEvento.cargarListado();
        masterData.setAll(eventos);
        tabla.setItems(eventos);
    }

    /**
     * Función que deshabilita o habilita los menus de edición
     *
     * @param deshabilitado los menus
     */
    private void deshabilitarMenus(boolean deshabilitado) {
        btnEditar.setDisable(deshabilitado);
        btnEliminar.setDisable(deshabilitado);
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