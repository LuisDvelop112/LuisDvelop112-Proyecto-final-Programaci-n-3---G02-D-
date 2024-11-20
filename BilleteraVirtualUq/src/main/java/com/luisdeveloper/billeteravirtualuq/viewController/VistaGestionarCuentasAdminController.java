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
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class VistaGestionarCuentasAdminController {

    CuentaController cuentaController = new CuentaController();
    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private TextField txtCuentaId;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtTipoCuenta;

    @FXML
    private TableView<Cuenta> tableCuentas;

    @FXML
    private TableColumn<Cuenta, String> tcCuentaId;

    @FXML
    private TableColumn<Cuenta, String> tcNombreBanco;

    @FXML
    private TableColumn<Cuenta, String> tcNumeroCuenta;

    @FXML
    private TableColumn<Cuenta, String> tcTipoCuenta;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    private final ObservableList<CuentaDto> cuentasList = FXCollections.observableArrayList();

    @FXML
    public void inicializar() {
        List<Cuenta> cuentas = null;
        tableCuentas.getItems().clear();

        // Inicializar las columnas con las propiedades de la clase Cuenta
        tcCuentaId.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
        tcNombreBanco.setCellValueFactory(new PropertyValueFactory<>("nombreBanco"));
        tcNumeroCuenta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        tcTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));

        tableCuentas.getItems().addAll(cuentas);

        // Agregar listener para la selección de filas
        tableCuentas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                llenarCamposConCuenta(newValue);
            }
        });
    }

    private void llenarCamposConCuenta(Cuenta newValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'llenarCamposConCuenta'");
    }

    @FXML
    void agregarCuentaAction(ActionEvent event) {
        try {
            // Crear nueva cuenta con los datos de los campos de texto
            CuentaDto nuevaCuenta = new CuentaDto(txtCuentaId.getText(), txtNombreBanco.getText(),
                    txtNumeroCuenta.getText(), txtTipoCuenta.getText());
            cuentaController.agregarCuentasUsuario(txtCuentaId.getText(), nuevaCuenta);
            limpiarCampos();
            inicializar(); // Recargar la tabla
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar la cuenta.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void actualizarCuentaAction(ActionEvent event) throws UsuarioNoEncontradoException, CuentaException {
        Cuenta cuentaSeleccionada = tableCuentas.getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada != null) {
            cuentaSeleccionada.setNombreBanco(txtNombreBanco.getText());
            cuentaSeleccionada.setNumeroCuenta(txtNumeroCuenta.getText());
            cuentaSeleccionada.setTipoCuenta(txtTipoCuenta.getText());
            CuentaDto nuevaCuenta = construirCuentaDto();
            cuentaController.actualizarCuenta(txtCuentaId.getText(), txtCuentaId.getText(), nuevaCuenta); // Asume que
                                                                                                          // existe este
                                                                                                          // método
            tableCuentas.refresh(); // Actualizar tabla
            limpiarCampos();
        } else {
            mostrarAlerta("Advertencia", "No se ha seleccionado ninguna cuenta.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarCuentaAction() {
        // Obtener la cuenta seleccionada
        Cuenta cuentaSeleccionada = tableCuentas.getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada != null) {
            // Eliminar la cuenta seleccionada de la lista
            cuentasList.remove(cuentaSeleccionada);

            // Limpiar los campos
            limpiarCampos();
        }
    }

    @FXML
    private void nuevoCuentaAction() {
        // Limpiar los campos para agregar una nueva cuenta
        limpiarCampos();
    }

    private void mostrarDetallesCuenta(Cuenta cuenta) {
        // Mostrar los detalles de la cuenta seleccionada en los TextFields
        txtCuentaId.setText(cuenta.getIdCuenta());
        txtNombreBanco.setText(cuenta.getNombreBanco());
        txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
        txtTipoCuenta.setText(cuenta.getTipoCuenta());
    }

    private void limpiarCampos() {
        // Limpiar todos los TextFields
        txtCuentaId.clear();
        txtNombreBanco.clear();
        txtNumeroCuenta.clear();
        txtTipoCuenta.clear();
    }

    private CuentaDto construirCuentaDto() {
        return new CuentaDto(txtCuentaId.getText(),
                txtNombreBanco.getText(),
                txtNumeroCuenta.getText(),
                txtTipoCuenta.getText());
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
