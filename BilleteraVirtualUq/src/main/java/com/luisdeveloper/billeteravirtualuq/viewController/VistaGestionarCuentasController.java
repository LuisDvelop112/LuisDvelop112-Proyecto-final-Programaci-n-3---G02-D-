package com.luisdeveloper.billeteravirtualuq.viewController;

import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.CuentaController;
import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaGestionarCuentasController {

    CuentaController cuentaController = new CuentaController();

    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

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

    @FXML
    private ObservableList<Cuenta> listaCuentas;

    @FXML
    public void inicializar(String idUsuario) {
        this.idUsuario = idUsuario;
        List<Cuenta> cuentas = cuentaController.obtenerCuentas(idUsuario);
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

    @FXML
    void agregarCuentaAction(ActionEvent event) {
        try {
            // Crear nueva cuenta con los datos de los campos de texto
            CuentaDto nuevaCuenta = new CuentaDto(txtCuentaId.getText(), txtNombreBanco.getText(), txtNumeroCuenta.getText(), txtTipoCuenta.getText());
            cuentaController.agregarCuentasUsuario(idUsuario, nuevaCuenta);
            limpiarCampos();
            inicializar(idUsuario); // Recargar la tabla
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
            cuentaController.actualizarCuenta(idUsuario, txtCuentaId.getText(), nuevaCuenta); // Asume que existe este método
            tableCuentas.refresh(); // Actualizar tabla
            limpiarCampos();
        } else {
            mostrarAlerta("Advertencia", "No se ha seleccionado ninguna cuenta.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void eliminarCuentaAction(ActionEvent event) {
        try {
            // Obtener la cuenta seleccionada en la tabla
            Cuenta cuentaSeleccionada = tableCuentas.getSelectionModel().getSelectedItem();
            if (cuentaSeleccionada != null) {

                cuentaController.eliminarCuenta(idUsuario, cuentaSeleccionada.getIdCuenta());
                inicializar(idUsuario);
                limpiarCampos();
                tableCuentas.refresh(); // Asegura que la tabla refleje los cambios
            } else {
                mostrarAlerta("Advertencia", "No se ha seleccionado ninguna cuenta.", Alert.AlertType.WARNING);
            }
        } catch (UsuarioNoEncontradoException e) {
            mostrarAlerta("Error", "Usuario no encontrado: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (CuentaException e) {
            mostrarAlerta("Error", "Error al eliminar la cuenta: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error inesperado", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    

    @FXML
    void nuevoCuentaAction(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCuentaId.clear();
        txtNombreBanco.clear();
        txtNumeroCuenta.clear();
        txtTipoCuenta.clear();
    }

    private void llenarCamposConCuenta(Cuenta cuenta) {
        txtCuentaId.setText(cuenta.getIdCuenta());
        txtNombreBanco.setText(cuenta.getNombreBanco());
        txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
        txtTipoCuenta.setText(cuenta.getTipoCuenta());
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private CuentaDto construirCuentaDto(){
        return new CuentaDto(txtCuentaId.getText(),
        txtNombreBanco.getText(),
        txtNumeroCuenta.getText(), 
        txtTipoCuenta.getText());
    }
}
