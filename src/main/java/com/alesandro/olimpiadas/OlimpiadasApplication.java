package com.alesandro.olimpiadas;

import com.alesandro.olimpiadas.language.LanguageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inicio - Olimpiadas");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Olimpiadas.png")));
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