package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import com.luisdeveloper.billeteravirtualuq.model.Usuario;

public class VistaGestionarUsuarioAdminController {

    // Controles de entrada de datos
    @FXML
    private TextField idUsuario;
    @FXML
    private TextField nombreCompleto;
    @FXML
    private TextField correoElectronico;
    @FXML
    private TextField numeroTelefono;
    @FXML
    private TextField direccion;
    @FXML
    private TextField saldoTotal;
    @FXML
    private PasswordField contrasena;

    // Botón para crear usuario
    @FXML
    private Button crearUsuario;

    // Tabla para mostrar los usuarios
    @FXML
    private TableView<Usuario> usuariosTable;
    @FXML
    private TableColumn<Usuario, String> idColumn;
    @FXML
    private TableColumn<Usuario, String> nombreColumn;
    @FXML
    private TableColumn<Usuario, String> correoColumn;

    // Controlador de usuario
    private UsuarioController usuarioController = new UsuarioController();

    @FXML
    private void initialize() {
        // Configurar columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));

        // Cargar usuarios en la tabla
        cargarUsuarios();

        // Configurar evento al seleccionar un usuario de la tabla
        usuariosTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedUser) -> {
            if (selectedUser != null) {
                setearDatosEnCampos(selectedUser);
            }
        });
    }

    private void setearDatosEnCampos(Usuario usuario) {
        idUsuario.setText(usuario.getIdUsuario());
        nombreCompleto.setText(usuario.getNombreCompleto());
        correoElectronico.setText(usuario.getCorreoElectronico());
        numeroTelefono.setText(usuario.getNumeroTelefono());
        direccion.setText(usuario.getDireccion());
        saldoTotal.setText(String.valueOf(usuario.getSaldoTotal()));
        contrasena.setText(usuario.getContrasena());
    }

    @FXML
    private void crearUsuario() {
        // Validar campos obligatorios
        if (validarCampos()) {
            // Crear un nuevo usuario
            UsuarioDto nuevoUsuario = construirUsuarioDto();
            // Guardar el usuario usando el controlador
            usuarioController.agregarUsuario(nuevoUsuario);

            limpiarCampos();
            cargarUsuarios();
        } else {
            System.out.println("Error: Todos los campos son obligatorios.");
        }
    }

    private boolean validarCampos() {
        return !idUsuario.getText().isEmpty() &&
                !nombreCompleto.getText().isEmpty() &&
                !correoElectronico.getText().isEmpty() &&
                !numeroTelefono.getText().isEmpty() &&
                !direccion.getText().isEmpty() &&
                !saldoTotal.getText().isEmpty() &&
                !contrasena.getText().isEmpty();
    }

    private void limpiarCampos() {
        idUsuario.clear();
        nombreCompleto.clear();
        correoElectronico.clear();
        numeroTelefono.clear();
        direccion.clear();
        saldoTotal.clear();
        contrasena.clear();
    }

    public UsuarioDto construirUsuarioDto() {
        return new UsuarioDto(idUsuario.getText(),
                nombreCompleto.getText(),
                correoElectronico.getText(),
                numeroTelefono.getText(),
                direccion.getText(),
                Double.parseDouble(saldoTotal.getText()),
                contrasena.getText(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public void cargarUsuarios() {
        usuariosTable.getItems().clear();
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        if (usuarios != null) {
            usuariosTable.getItems().addAll(usuarios);
        }
    }

    @FXML
    private void actualizarUsuario() throws UsuarioNoEncontradoException{
        // Validar campos obligatorios
        if (validarCampos()) {
            // Crear un nuevo usuario
            UsuarioDto nuevoUsuario = construirUsuarioDto();
            // Guardar el usuario usando el controlador
            usuarioController.actualizarUsuario(idUsuario.getText(), nuevoUsuario);

            limpiarCampos();
            cargarUsuarios();
        } else {
            System.out.println("Error: Todos los campos son obligatorios.");
        }
    }

    @FXML
    private void eliminarUsuario() throws UsuarioNoEncontradoException{
        // Validar campos obligatorios
        if (validarCampos()) {
            
            usuarioController.eliminarUsuario(idUsuario.getText());

            limpiarCampos();
            cargarUsuarios();
        } else {
            System.out.println("Error: Todos los campos son obligatorios.");
        }
    }

}
