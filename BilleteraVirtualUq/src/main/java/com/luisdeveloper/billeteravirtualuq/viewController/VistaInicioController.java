package com.luisdeveloper.billeteravirtualuq.viewController;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class VistaInicioController {

    
    // Method to handle the "Iniciar Sesión" button click
    @FXML
    protected void iniciarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaIniciarSesion.fxml"));
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

    // Method to handle the "Registrarse" button click
    @FXML
    protected void registrarse(ActionEvent event) {
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
    

    // Utility method to show alerts
    private void mostrarAlerta(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
