package com.alesandro.proyecto1olimpiadas;

import com.alesandro.proyecto1olimpiadas.db.DBConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Clase donde se ejecuta la aplicación principal
 *
 * @author alesandroquirosgobbato
 */
public class OlimpiadasApplication extends Application {
    /**
     * {@inheritDoc}
     *
     * Función donde se carga y se muestra la ventana de la aplicación
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el idioma
        Properties properties = DBConnect.getConfiguracion();
        String language = properties.getProperty("language");
        Locale locale = new Locale.Builder().setLanguage(language).build();
        ResourceBundle bundle = ResourceBundle.getBundle("languages/lang", locale);
        // Lanzar aplicación
        FXMLLoader fxmlLoader = new FXMLLoader(OlimpiadasApplication.class.getResource("/fxml/Inicio.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio - Olimpiadas");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Función main donde se lanza la aplicación
     *
     * @param args parámetros por consola
     */
    public static void main(String[] args) {
        Application.launch();
    }
}