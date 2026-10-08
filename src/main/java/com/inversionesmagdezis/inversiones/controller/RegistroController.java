/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.java.com.inversionesmagdezis.inversiones.controller;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.com.inversionesmagdezis.inversiones.service.AuthService;

public class RegistroController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private TextField txtContrasenaVisible;
    @FXML private Button btnTogglePassword;
    @FXML private ComboBox<String> cmbRol;
    @FXML private Button btnRegresarLogin;
    @FXML private ImageView imgBanner;

    private boolean mostrandoPassword = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbRol.setItems(FXCollections.observableArrayList("1 - Administrador", "2 - Cliente"));
    }    

    

    @FXML
    private void handleRegistrar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();
        
        String contrasena = mostrandoPassword ? txtContrasenaVisible.getText().trim() : txtContrasena.getText().trim();

        String rolSeleccionado = cmbRol.getValue();
        if (rolSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Por favor seleccione un rol para el usuario.");
            return;
        }

        int idRol = Character.getNumericValue(rolSeleccionado.charAt(0));
        boolean registrado = AuthService.registrarUsuario(nombre, apellido, nombreUsuario, correo, contrasena, idRol);

        if (registrado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Usuario registrado correctamente con rol " + idRol + "!");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Registro", "No se pudo registrar. Verifique los campos o si el correo/usuario ya existen.");
        }
    }

    @FXML
    private void handleRegresarLogin(ActionEvent event) {
        try {
            Stage stageActual = (Stage) btnRegresarLogin.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(stageActual);
            sceneManager.showLoginView();
        } catch (Exception e) {
            System.err.println("Error al regresar al login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtNombreUsuario.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        txtContrasenaVisible.clear();
        cmbRol.getSelectionModel().clearSelection();
        if (mostrandoPassword) {
            handleTogglePassword(null);
        }
    }
}