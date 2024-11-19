package com.luisdeveloper.billeteravirtualuq.viewController;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class VistaRetirarSaldoController {

    UsuarioController usuarioController = new UsuarioController();

    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo o vacío.");
        }
        this.idUsuario = idUsuario;
    }

    @FXML
    private TextField saldo; // Campo para ingresar el saldo a retirar

    @FXML
    private Button btnRetirar; // Botón para retirar el saldo

    // Acción que se ejecuta al presionar el botón "Retirar"
    @FXML
    private void retirarSaldo() {
        // Obtener el valor ingresado en el campo de texto
        String saldoText = saldo.getText();
        
        // Verificar si el campo no está vacío y si el valor es un número válido
        if (saldoText == null || saldoText.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Error", "Por favor, ingrese un monto válido.");
            return;
        }
        
        try {
            // Convertir el texto a un valor numérico
            double monto = Double.parseDouble(saldoText);
            if(usuarioController.reducirSaldo(idUsuario, monto)){
                mostrarAlerta(AlertType.INFORMATION, "Retiro Exitoso", "Has retirado " + monto + " unidades de saldo.");
            }else{
                mostrarAlerta(AlertType.INFORMATION, "Retiro Fallido", "No hay suficiente saldo en la cuenta.");
            }
            // Aquí puedes agregar lógica para realizar la operación de retiro (verificar saldo disponible, realizar la transacción, etc.)
            // En este caso, solo mostramos el mensaje de confirmación
            

            // Limpiar el campo de texto después de realizar el retiro
            saldo.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta(AlertType.ERROR, "Error", "El monto ingresado no es válido.");
        }
    }

    // Método para mostrar alertas
    private void mostrarAlerta(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
