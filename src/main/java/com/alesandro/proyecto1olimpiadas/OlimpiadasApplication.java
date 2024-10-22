package com.alesandro.proyecto1olimpiadas;

import com.alesandro.proyecto1olimpiadas.language.LanguageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
        ResourceBundle bundle = LanguageManager.getInstance().getBundle();
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