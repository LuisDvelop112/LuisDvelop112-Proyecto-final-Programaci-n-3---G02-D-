package com.luisdeveloper.billeteravirtualuq.viewController;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class VistaActualizarPerfilController {

    UsuarioController usuarioController = new UsuarioController();

    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo o vacío.");
        }
        this.idUsuario = idUsuario;
    }

    // Definición de los campos del formulario
    @FXML
    private TextField Nombre;

    @FXML
    private TextField Apellido;

    @FXML
    private TextField Correo;

    @FXML
    private TextField Telefono;

    @FXML
    private TextField Direccion;

    @FXML
    private void ActualizarDatos(ActionEvent event) throws UsuarioNoEncontradoException {
        // Obtener valores de los campos
        String nombre = Nombre.getText();
        String apellido = Apellido.getText();
        String correo = Correo.getText();
        String telefono = Telefono.getText();
        String direccion = Direccion.getText();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios. Por favor, complételos.");
            return;
        }else{
            UsuarioDto Usuario = construirUsuario();
            usuarioController.actualizarUsuario(idUsuario, Usuario);
        }

        // Validación básica del correo electrónico
        /*if (!correo.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            mostrarAlerta("Error", "El correo electrónico no tiene un formato válido.");
            return;
        }*/

        // Simulación de actualización de datos
        mostrarAlerta("Éxito", "Los datos se han actualizado correctamente:\n" +
                "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Correo: " + correo + "\n" +
                "Teléfono: " + telefono + "\n" +
                "Dirección: " + direccion);
        
        // Opcional: limpiar los campos después de la actualización
        limpiarCampos();
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        Nombre.clear();
        Apellido.clear();
        Correo.clear();
        Telefono.clear();
        Direccion.clear();
    }

    private UsuarioDto construirUsuario() {
        // Obtener los datos del usuario actual
        UsuarioDto user = usuarioController.obtenerUsuario(idUsuario);
    
        // Construir el nombre completo
        String nombreCompleto = Nombre.getText() + " " + Apellido.getText();
    
        // Crear una instancia de UsuarioDtoImpl
        UsuarioDto usuarioDto = new UsuarioDto(
            idUsuario,                               // ID del usuario
            nombreCompleto,                          // Nombre completo
            Correo.getText(),                        // Correo electrónico
            Telefono.getText(),                      // Teléfono
            Direccion.getText(),                     // Dirección
            user.saldoTotal(),                       // Saldo total
            user.contrasena(),                       // Contraseña
            user.cuentasBancarias(),                 // Lista de cuentas bancarias
            user.transacciones(),                    // Lista de transacciones
            user.presupuestos()                      // Lista de presupuestos
        );
    
        return usuarioDto;
    }
    
}
