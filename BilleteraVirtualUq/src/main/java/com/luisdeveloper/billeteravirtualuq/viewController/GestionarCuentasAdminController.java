package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.CuentaController;
import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;
import com.luisdeveloper.billeteravirtualuq.model.Usuario;

public class GestionarCuentasAdminController {

    private final CuentaController cuentaController = new CuentaController();
    private final UsuarioController usuarioController = new UsuarioController();

    @FXML
    private TextField idUsuario;
    @FXML
    private TextField idCuenta;
    @FXML
    private TextField nombreBanco;
    @FXML
    private TextField numeroCuenta;
    @FXML
    private TextField tipoCuenta;

    @FXML
    private TableView<Cuenta> cuentasTable;
    @FXML
    private TableColumn<Cuenta, String> idCuentaColumn;
    @FXML
    private TableColumn<Cuenta, String> nombreBancoColumn;
    @FXML
    private TableColumn<Cuenta, String> numeroCuentaColumn;
    @FXML
    private TableColumn<Cuenta, String> tipoCuentaColumn;

    @FXML
    private void initialize() {
        // Configurar columnas de la tabla
        idCuentaColumn.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
        nombreBancoColumn.setCellValueFactory(new PropertyValueFactory<>("nombreBanco"));
        numeroCuentaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        tipoCuentaColumn.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));

        cargarCuentas();
    }

    @FXML
    private void crearCuenta() {
        if (validarCampos()) {
            CuentaDto nuevaCuenta = construirCuenta();
            cuentaController.agregarCuentasUsuario(idUsuario.getText(), nuevaCuenta);
            limpiarCampos();
            cargarCuentas();
        } else {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
        }
    }

    @FXML
    private void actualizarCuenta() throws UsuarioNoEncontradoException, CuentaException {
        if (validarCampos()) {
            CuentaDto cuentaActualizada = construirCuenta();
            cuentaController.actualizarCuenta(idUsuario.getText(), idCuenta.getText(), cuentaActualizada);
            limpiarCampos();
            cargarCuentas();
        } else {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
        }
    }

    @FXML
    private void eliminarCuenta() throws UsuarioNoEncontradoException, CuentaException {
        if (!idCuenta.getText().isEmpty()) {
            cuentaController.eliminarCuenta(idUsuario.getText(), idCuenta.getText());
            limpiarCampos();
            cargarCuentas();
        } else {
            mostrarAlerta("Error", "Debe ingresar el ID de la cuenta para eliminarla.");
        }
    }

    private CuentaDto construirCuenta() {
        return new CuentaDto(
            idUsuario.getText(),
            nombreBanco.getText(),
            numeroCuenta.getText(),
            tipoCuenta.getText()
        );
    }

    private boolean validarCampos() {
        return !idUsuario.getText().isEmpty() &&
                !idCuenta.getText().isEmpty() &&
                !nombreBanco.getText().isEmpty() &&
                !numeroCuenta.getText().isEmpty() &&
                !tipoCuenta.getText().isEmpty();
    }

    private void limpiarCampos() {
        idUsuario.clear();
        idCuenta.clear();
        nombreBanco.clear();
        numeroCuenta.clear();
        tipoCuenta.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void cargarCuentas() {
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        List<Cuenta> cuentas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            List<Cuenta> cuentasUsuario = cuentaController.obtenerCuentas(usuario.getIdUsuario());
            if (cuentasUsuario != null) {
                cuentas.addAll(cuentasUsuario);
            }
        }

        // Limpiar la tabla antes de agregar los nuevos datos
        cuentasTable.getItems().clear();

        // Agregar todas las cuentas a la tabla
        cuentasTable.getItems().addAll(cuentas);
    }
}
