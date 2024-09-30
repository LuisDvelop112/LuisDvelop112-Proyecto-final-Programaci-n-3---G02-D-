package com.luisdeveloper.billeteravirtualuq.viewController;

import com.luisdeveloper.billeteravirtualuq.controller.CuentaController;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CuentaViewController {

    CuentaController cuentaControllerService;
    ObservableList<CuentaDto> listaCuentasDto = FXCollections.observableArrayList();
    CuentaDto cuentaSeleccionada;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TextField txtCuentaId;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtTipoCuenta;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<CuentaDto> tableCuentas;

    @FXML
    private TableColumn<CuentaDto, String> tcCuentaId;

    @FXML
    private TableColumn<CuentaDto, String> tcNombreBanco;

    @FXML
    private TableColumn<CuentaDto, String> tcNumeroCuenta;

    @FXML
    private TableColumn<CuentaDto, String> tcTipoCuenta;



    @FXML
    void initialize() {
        cuentaControllerService = new CuentaController();
        intiView();
    }

    private void intiView() {
        initDataBinding();
        obtenerCuenta();
        tableCuentas.getItems().clear();
        tableCuentas.setItems(listaCuentasDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcCuentaId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().idCuenta()));

        tcNombreBanco.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreBanco()));

        tcNumeroCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().numeroCuenta()));

        tcTipoCuenta.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipoCuenta()));

    }

    private void obtenerCuenta() {
        listaCuentasDto.addAll(cuentaControllerService.obtenerCuentas());
    }

    private void listenerSelection() {
        tableCuentas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            cuentaSeleccionada = newSelection;
            mostrarInformacionCuenta(cuentaSeleccionada);
        });
    }

    private void mostrarInformacionCuenta(CuentaDto cuentaSeleccionada) {
        if(cuentaSeleccionada != null){
            txtCuentaId.setText(cuentaSeleccionada.idCuenta());

            txtNumeroCuenta.setText(cuentaSeleccionada.numeroCuenta());

            txtNombreBanco.setText(cuentaSeleccionada.nombreBanco());

            txtTipoCuenta.setText(cuentaSeleccionada.tipoCuenta());
        }
    }

    @FXML
    void nuevoCuentaAction(ActionEvent event) {
        txtCuentaId.setText("Ingrese Id de la cuenta");

        txtNumeroCuenta.setText("Ingrese numero de cuenta");

        txtNombreBanco.setText("Ingrese nombre del banco");

        txtTipoCuenta.setText("Ingrese tipo de cuenta");
    }

    @FXML
    void agregarCuentaAction(ActionEvent event) {
        crearCuenta();
    }

    @FXML
    void eliminarCuentaAction(ActionEvent event) {
        eliminarCuenta();
    }


    @FXML
    void actualizarCuentaAction(ActionEvent event) {
        actualizarCuenta();
    }

    private void crearCuenta() {
        //1. Capturar los datos
        CuentaDto cuentaDto = construirCuentaDto();
        //2. Validar la información
        if(datosValidos(cuentaDto)){
            if(cuentaControllerService.agregarCuenta(cuentaDto)){
                listaCuentasDto.add(cuentaDto);
                mostrarMensaje("Notificación Cuenta", "Cuenta creada", "La Cuenta se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposCuenta();
            }else{
                mostrarMensaje("Notificación Cuenta", "Cuenta no creada", "La Cuenta no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación Cuenta", "Cuenta no creada", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarCuenta() {
        boolean cuentaEliminado = false;
        if(cuentaSeleccionada != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar la Cuenta?")){
                cuentaEliminado = cuentaControllerService.eliminarCuenta(cuentaSeleccionada.idCuenta());
                if(cuentaEliminado == true){
                    listaCuentasDto.remove(cuentaSeleccionada);
                    cuentaSeleccionada = null;
                    tableCuentas.getSelectionModel().clearSelection();
                    limpiarCamposCuenta();
                    mostrarMensaje("Notificación Cuenta", "Cuenta eliminada", "La Cuenta se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación Cuenta", "Cuenta no eliminada", "La Cuenta no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Seleccionado un empleado de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarCuenta() {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String idCuentaActual = cuentaSeleccionada.idCuenta();
        CuentaDto cuentaDto = construirCuentaDto();
        //2. verificar el empleado seleccionado
        if(cuentaSeleccionada != null){
            //3. Validar la información
            if(datosValidos(cuentaSeleccionada)){
                clienteActualizado = cuentaControllerService.actualizarCuenta(idCuentaActual,cuentaDto);
                if(clienteActualizado){
                    listaCuentasDto.remove(cuentaSeleccionada);
                    listaCuentasDto.add(cuentaDto);
                    tableCuentas.refresh();
                    mostrarMensaje("Notificación Cuenta", "Cuenta actualizada", "La Cuenta se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposCuenta();
                }else{
                    mostrarMensaje("Notificación Cuenta", "Cuenta no actualizada", "La Cuenta no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación Cuenta", "Cuenta no creada", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private CuentaDto construirCuentaDto() {
        return new CuentaDto(
                txtCuentaId.getText(),
                txtNumeroCuenta.getText(),
                txtNombreBanco.getText(),
                txtTipoCuenta.getText());
    }

    private void limpiarCamposCuenta() {
        txtCuentaId.setText("");

        txtNumeroCuenta.setText("");

        txtNombreBanco.setText("");

        txtTipoCuenta.setText("");
    }

    private boolean datosValidos(CuentaDto cuentaDto) {
        String mensaje = "";
        if(cuentaDto.idCuenta() == null || cuentaDto.idCuenta().equals(""))
            mensaje += "El id es invalido \n" ;
        if(cuentaDto.nombreBanco() == null || cuentaDto.nombreBanco().equals(""))
            mensaje += "El nombre del banco es invalido \n" ;
        if(cuentaDto.numeroCuenta() == null || cuentaDto.numeroCuenta().equals(""))
            mensaje += "El numero del banco es invalido \n" ;
        if(cuentaDto.tipoCuenta() == null || cuentaDto.tipoCuenta().equals(""))
            mensaje += "El tipo del cuenta es invalido \n" ;

        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
