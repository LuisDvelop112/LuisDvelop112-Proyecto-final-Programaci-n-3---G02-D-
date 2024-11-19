package com.luisdeveloper.billeteravirtualuq.viewController;

import java.io.IOException;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VistaInicioSesionController {

    private UsuarioController usuarioController = new UsuarioController();
    VistaPrincipalController vistaPrincipalController = new VistaPrincipalController();

    @FXML
    private TextField Usuario;

    @FXML
    private TextField Contrasena;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    // Método para manejar el evento del botón "Iniciar Sesión"
    @FXML
    private void iniciarSesion(ActionEvent event) {
        String usuario = Usuario.getText();
        String contrasena = Contrasena.getText();
    
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            System.out.println("Por favor, ingrese el usuario y la contraseña.");
        } else {
            System.out.println("Iniciando sesión con usuario: " + usuario);
            if (usuarioController.iniciarSesion(usuario, contrasena)) {
                if ("Admin".equalsIgnoreCase(usuario)) { // Verifica si el usuario es "Admin"
                    cargarVistaAdmin(); // Método para cargar la vista de administrador
                } else {
                    cargarVistaPrincipal(); // Método para cargar la vista de usuario normal
                }
            } else {
                System.out.println("Credenciales incorrectas.");
            }
        }
    }
    

    // Método para manejar el evento del botón "Registrarse"
    @FXML
    private void registrarse(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaRegistro.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Registro de Usuario");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de registro. Verifique la ruta del archivo FXML.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Archivo FXML no encontrado. Verifique la ubicación de RegistroView.fxml.");
        }
    }
    

    private void cargarVistaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaPrincipal.fxml"));
            Parent root = loader.load();
            VistaPrincipalController vistaPrincipalController = loader.getController();
            vistaPrincipalController.setIdUsuario(Usuario.getText());
            Stage stage = new Stage();
            stage.setTitle("Vista Principal");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista principal.");
        }
    }

    private void cargarVistaAdmin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaPrincipalAdmin.fxml"));
            Parent root = loader.load();
            VistaPrincipalControllerAdmin vistaPrincipalController = loader.getController();
            vistaPrincipalController.setIdUsuario(Usuario.getText());
            Stage stage = new Stage();
            stage.setTitle("Vista Principal");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista principal.");
        }
    }
    


    private void mostrarAlerta(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
