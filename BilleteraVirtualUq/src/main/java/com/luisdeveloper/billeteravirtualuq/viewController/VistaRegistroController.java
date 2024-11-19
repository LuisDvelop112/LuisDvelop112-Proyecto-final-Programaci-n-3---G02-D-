package com.luisdeveloper.billeteravirtualuq.viewController;

import java.util.ArrayList;
import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.PresupuestoDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class VistaRegistroController {
    private UsuarioController usuarioController = new UsuarioController();

    @FXML
    private TextField id_cliente;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField correo;
    @FXML
    private TextField telefono;
    @FXML
    private TextField direccion;
    @FXML
    private TextField contrasena;

    // Method to handle the registration process when the "Registrar" button is
    // clicked
    @FXML
    protected void Registrar_Usuario(ActionEvent event) {
        UsuarioDto usuario;
        usuario = construirUsuarioDto();
        usuarioController.agregarUsuario(usuario);
        showAlert(Alert.AlertType.INFORMATION, "Registro exitoso", "Usuario registrado exitosamente!");
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private UsuarioDto construirUsuarioDto() {

        String idCliente = id_cliente.getText();
        String nombreUsuario = nombre.getText();
        String apellidoUsuario = apellido.getText();
        String correoElectronico = correo.getText();
        String numeroTelefono = telefono.getText();
        String direccionUsuario = direccion.getText();
        List<CuentaDto> cuenta = new ArrayList<>();
        List<TransaccionDto> transacciones = new ArrayList<>();
        List<PresupuestoDto> presupuestos = new ArrayList<>();

        // Basic validation
        if (idCliente.isEmpty() || nombreUsuario.isEmpty() || apellidoUsuario.isEmpty() ||
                correoElectronico.isEmpty() || numeroTelefono.isEmpty() || direccionUsuario.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, complete todos los campos.");
            return null;
        }
        String nombreU = nombre.getText() + apellido.getText();

        return new UsuarioDto(
                id_cliente.getText(),
                nombreU,
                correo.getText(),
                telefono.getText(),
                direccion.getText(),
                0,
                contrasena.getText(),
                cuenta,
                transacciones,
                presupuestos);
    }

}
