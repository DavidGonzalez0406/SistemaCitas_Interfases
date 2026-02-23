package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 1. Cargamos la primera pantalla (Aviso de Privacidad)
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/example/Privacidad.fxml"));

        // 2. Seteamos el tamaño de celular (360x640)
        Scene scene = new Scene(fxmlLoader.load(), 360, 640);

        // 3. CARGA DEL CSS (Paso crítico)
        // Usamos "/org/example/estilos.css" porque es la ruta dentro de resources
        String css = getClass().getResource("/org/example/estilos.css").toExternalForm();
        scene.getStylesheets().add(css);

        // 4. Configuración de la ventana
        stage.setTitle("Sistema de Citas"); // Tu nombre para la autoría
        stage.setScene(scene);

        // 5. Bloqueamos el tamaño para que no se mueva el diseño en la revisión
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}