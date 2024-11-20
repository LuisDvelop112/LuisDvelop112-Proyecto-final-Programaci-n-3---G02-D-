package com.luisdeveloper.billeteravirtualuq.viewController;

import java.io.IOException;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class VistaPrincipalController {

    UsuarioController usuarioController = new UsuarioController();
    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo o vacío.");
        }
        this.idUsuario = idUsuario;
    }

    @FXML
    private void editarPerfil(ActionEvent event) {
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
                    "No se pudo abrir la ventana 'Editar Perfil'. Verifique la ruta del archivo FXML.");
        }
    }

    @FXML
    private void crearReportes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaCrearReportes.fxml"));
            Parent root = loader.load();
            VistaReportesController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Crear Reportes");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana 'Crear Reportes'. Verifique la ruta del archivo FXML.");
        }
    }

    @FXML
    private void chatBot(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaChatBot.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Chat Bot");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana 'Chat Bot'. Verifique la ruta del archivo FXML.");
        }
    }

    @FXML
    private void consultarSaldo(ActionEvent event) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Error", "El ID de usuario no está establecido.");
            return;
        }
        double saldo = usuarioController.obtenerSaldo(idUsuario);
        mostrarMensaje("Saldo", "El saldo del usuario: " + idUsuario + " es: " + saldo);
    }

    @FXML
    private void agregarSaldo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaAgregarSaldo.fxml"));
            Parent root = loader.load();
            VistaAgregarSaldoController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Agregar Saldo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana 'Agregar Saldo'. Verifique la ruta del archivo FXML.");
        }
    }

    @FXML
    private void retirarSaldo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaRetirarSaldo.fxml"));
            Parent root = loader.load();
            VistaRetirarSaldoController controller = loader.getController();
            controller.setIdUsuario(idUsuario);
            Stage stage = new Stage();
            stage.setTitle("Retirar Saldo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir la ventana 'Retirar Saldo'. Verifique la ruta del archivo FXML.");
        }
    }

    @FXML
    private void gestionarTransaccion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaGestionarTransacciones.fxml"));
            Parent root = loader.load();
            VistaGestionarTransaccionesController controller = loader.getController();
            controller.setIdUsuario(idUsuario); // Se pasa el ID de usuario
            controller.setearSaldo(idUsuario);
            controller.cargarTransacciones();
            Stage stage = new Stage();
            stage.setTitle("gestionar Transacciones");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de consulta de transacciones.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de VistaConsultarTransacciones.fxml.");
        }
    }

    @FXML
    private void gestionarPresupuestos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaGestionarPresupuestos.fxml"));
            Parent root = loader.load();
            VistaGestionarPresupuestosController controller = loader.getController();
            controller.setIdUsuario(idUsuario); // Se pasa el ID de usuario
            controller.cargarTablaPresupuestos();
            Stage stage = new Stage();
            stage.setTitle("Gestionar Presupuestos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de gestión de presupuestos.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de VistaGestionarPresupuestos.fxml.");
        }
    }

    @FXML
    private void gestionarCuentas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaGestionarCuentas.fxml"));
            Parent root = loader.load();
            VistaGestionarCuentasController controller = loader.getController();
            controller.inicializar(idUsuario);
            controller.setIdUsuario(idUsuario); // Se pasa el ID de usuario
            Stage stage = new Stage();
            stage.setTitle("Gestionar Cuentas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de gestion de Cuentas.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de VistaGestionarCuentas.fxml.");
        }
    }

    @FXML
    protected void consultarTransacciones() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaDetalleTransaccion.fxml"));
            Parent root = loader.load();
            VistaDetalleTransaccionesController controller = loader.getController();
            controller.setIdUsuario(idUsuario); // Se pasa el ID de usuario
            Stage stage = new Stage();
            stage.setTitle("Gestionar Transacciones");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de gestión de Transacciones.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de VistaConsultarTransacciones.fxml.");
        }
    }

    @FXML
    protected void crearCategoria() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/luisdeveloper/billeteravirtualuq/VistaCrearCategoria.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Crear Categoria");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de creacion de Categorias.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Archivo FXML no encontrado. Verifique la ubicación de VistaCrearCategoria.fxml.");
        }
    }

    private void mostrarAlerta(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void guardarDatos() throws Exception {
        usuarioController.guardarDatos();
    }

}
