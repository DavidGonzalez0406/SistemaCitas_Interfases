package org.example;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private ImageView imgPerfil, imgFotoRegistro, imgFotoPerfil;
    @FXML private Label lblNombreEdad, lblBio, lblNombrePersona, lblEdadCalculada;
    @FXML private ComboBox<String> cbGenero, cbModoContacto;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private CheckBox checkAcepto;
    @FXML private Button btnContinuar;

    private static int indiceActual = 0;

    private final String[] FOTOS = {"claudia.png", "Salma_Hayek_Deauville_2012.jpg", "cachi.jpg", "channels4_profile.jpg"};
    private final String[] NOMBRES = {"Claudia", "Salma", "Cachi", "Kali"};
    private final int[] EDADES = {22, 24, 22, 21};
    private final String[] BIOS = {
            "Amante de los viajes y la fotografía. ☕",
            "Fan del cine clásico y la buena comida. 💃",
            "Me gusta programar, si tú sabes, ya ganaste conmigo. 💻",
            "Adicta al gym y la vida saludable. 🍎"
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (cbGenero != null) cbGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));
        if (cbModoContacto != null) cbModoContacto.setItems(FXCollections.observableArrayList("WhatsApp", "Instagram", "Telegram"));

        // Carga y hace circular la foto de registro
        if (imgFotoRegistro != null) {
            cargarImagen(imgFotoRegistro, "default.jpg");
            hacerCircular(imgFotoRegistro);
        }

        if (imgPerfil != null) actualizarInterfazFeed();
        if (imgFotoPerfil != null) actualizarInterfazContacto();
    }

    // --- MAGIA PARA LA FOTO REDONDA ---
    private void hacerCircular(ImageView iv) {
        Circle clip = new Circle(iv.getFitWidth() / 2, iv.getFitHeight() / 2, iv.getFitWidth() / 2);
        iv.setClip(clip);
    }

    private void cargarImagen(ImageView iv, String nombreArchivo) {
        try {
            InputStream is = getClass().getResourceAsStream("/" + nombreArchivo);
            if (is != null) iv.setImage(new Image(is));
        } catch (Exception e) { System.err.println("No se encontró: " + nombreArchivo); }
    }

    private void actualizarInterfazFeed() {
        cargarImagen(imgPerfil, FOTOS[indiceActual]);
        lblNombreEdad.setText(NOMBRES[indiceActual] + ", " + EDADES[indiceActual]);
        lblBio.setText(BIOS[indiceActual]);
    }

    private void actualizarInterfazContacto() {
        cargarImagen(imgFotoPerfil, FOTOS[indiceActual]);
        if (lblNombrePersona != null) lblNombrePersona.setText(NOMBRES[indiceActual]);
        if (lblBio != null) lblBio.setText(BIOS[indiceActual]);
    }

    @FXML private void siguientePerfil() {
        indiceActual = (indiceActual + 1) % FOTOS.length;
        actualizarInterfazFeed();
    }

    @FXML private void calcularEdad() {
        if (dpFechaNacimiento != null && dpFechaNacimiento.getValue() != null) {
            LocalDate fechaNac = dpFechaNacimiento.getValue();
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            if (lblEdadCalculada != null) lblEdadCalculada.setText("Edad: " + edad + " años");
        }
    }

    private void cambiarEscena(ActionEvent event, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/" + fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 360, 640);
        URL cssURL = getClass().getResource("/org/example/estilos.css");
        if (cssURL != null) scene.getStylesheets().add(cssURL.toExternalForm());
        stage.setScene(scene);
    }

    @FXML private void irARegistro(ActionEvent e) throws IOException { cambiarEscena(e, "Registro.fxml"); }
    @FXML private void irAExtras(ActionEvent e) throws IOException { cambiarEscena(e, "Opcionales.fxml"); }
    @FXML private void irAFeed(ActionEvent e) throws IOException { cambiarEscena(e, "Feed.fxml"); }
    @FXML private void irAMensajes(ActionEvent e) throws IOException { cambiarEscena(e, "Mensajes.fxml"); }
    @FXML private void irAContacto(ActionEvent e) throws IOException { cambiarEscena(e, "Contacto.fxml"); }
    @FXML private void irAPrivacidad(ActionEvent e) throws IOException { cambiarEscena(e, "Privacidad.fxml"); }
    @FXML private void irAIntegrantes(ActionEvent e) throws IOException { cambiarEscena(e, "Integrantes.fxml"); }

    @FXML private void validarCheck() { if(btnContinuar != null) btnContinuar.setDisable(!checkAcepto.isSelected()); }

    @FXML private void enviarSolicitud(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("LoveMatch"); a.setContentText("¡Solicitud enviada a " + NOMBRES[indiceActual] + "!");
        a.showAndWait();
    }

    @FXML private void aceptarSolicitud() { System.out.println("Ok"); }
    @FXML private void rechazarSolicitud() { System.out.println("No"); }
}