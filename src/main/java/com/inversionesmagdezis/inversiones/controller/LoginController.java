package main.java.com.inversionesmagdezis.inversiones.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.com.inversionesmagdezis.inversiones.dto.request.LoginDTORequest;
import main.java.com.inversionesmagdezis.inversiones.dto.response.LoginDTOResponse;
import main.java.com.inversionesmagdezis.inversiones.repository.AuthRepository;
import main.java.com.inversionesmagdezis.inversiones.service.AuthService;
import main.java.com.inversionesmagdezis.inversiones.util.SceneManager;

public class LoginController{
@FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnGoToRegister;

    

    private final AuthService authService = new AuthService(new AuthRepository());

    @FXML
    public void initialize() {
        
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, complete todos los campos para continuar.");
            return;
        }

        try {
            LoginDTORequest request = new LoginDTORequest(email, password);
            
            LoginDTOResponse response = authService.login(request);

            if (response == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Acceso Denegado", "Correo o contraseña incorrectos.");
                return;
            }

            String rolUsuario = response.getNombreRol();
            System.out.println(" Login exitoso para: " + response.getNombre() + "  Rol obtenido de BD: " + rolUsuario );

            SceneManager.setRolUsuarioActual(rolUsuario);

            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(currentStage);
            sceneManager.showMainDashboard();
            
        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Autenticación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Sistema", "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        try {
            Stage currentStage = (Stage) btnGoToRegister.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(currentStage);
            sceneManager.cargarVistaFisica("src/main/resources/view/registro-view.fxml", null, "Inversiones Magdezis - Registro de Usuario");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Navegación", "No se pudo abrir la vista de registro: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    

}
}
