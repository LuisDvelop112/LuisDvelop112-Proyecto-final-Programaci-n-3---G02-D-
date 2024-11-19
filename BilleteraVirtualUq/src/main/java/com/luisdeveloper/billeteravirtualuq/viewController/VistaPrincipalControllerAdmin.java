package com.luisdeveloper.billeteravirtualuq.viewController;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class VistaPrincipalControllerAdmin {

    String idUsuario;

    // Método que se llama cuando se hace clic en "Gestionar cuentas"
    @FXML
    private void gestionarCuentas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaGestionarCuentasAdmin.fxml"));
            Parent root = loader.load();
            VistaActualizarPerfilController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana de registro. Verifique la ruta del archivo FXML.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de RegistroView.fxml.");
        }
    }

    // Método que se llama cuando se hace clic en "Gestionar usuarios"
    @FXML
    private void gestionarUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaActualizarPerfil.fxml"));
            Parent root = loader.load();
            VistaActualizarPerfilController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana de registro. Verifique la ruta del archivo FXML.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de RegistroView.fxml.");
        }
    }

    // Método que se llama cuando se hace clic en "Gestionar transacciones"
    @FXML
    private void gestionarTransacciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaActualizarPerfil.fxml"));
            Parent root = loader.load();
            VistaActualizarPerfilController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana de registro. Verifique la ruta del archivo FXML.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de RegistroView.fxml.");
        }
    }

    // Método que se llama cuando se hace clic en "Gestionar presupuestos"
    @FXML
    private void gestionarPresupuestos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaActualizarPerfil.fxml"));
            Parent root = loader.load();
            VistaActualizarPerfilController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Editar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana de registro. Verifique la ruta del archivo FXML.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de RegistroView.fxml.");
        }
    }

    // Método auxiliar para mostrar una alerta
    private void mostrarAlerta(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
