package com.luisdeveloper.billeteravirtualuq.viewController;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class VistaAgregarSaldoController {
    UsuarioController usuarioController = new UsuarioController();

    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo o vacío.");
        }
        this.idUsuario = idUsuario;
    }

    @FXML
    private TextField saldo;

    @FXML
    private void agregarSaldo() {
        // Valida que haya un ID de usuario y un saldo válido
        if (idUsuario == null || idUsuario.isEmpty()) {
            mostrarAlerta("Error", "No se ha establecido el ID del usuario.");
            return;
        }

        try {
            double monto = Double.parseDouble(saldo.getText());
            if (monto <= 0) {
                mostrarAlerta("Error", "Ingrese un monto válido mayor a 0.");
                return;
            }

            // Aquí va la lógica para agregar saldo (ejemplo):
            System.out.println("Saldo agregado para el usuario " + idUsuario + " es: " + monto);
            usuarioController.aumentarSaldo(idUsuario, monto);
            mostrarAlerta("Éxito", "Saldo agregado exitosamente.");

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un monto válido.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
