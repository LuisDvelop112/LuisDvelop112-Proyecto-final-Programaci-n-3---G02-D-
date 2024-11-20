package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.TransaccionController;
import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;
import com.luisdeveloper.billeteravirtualuq.model.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestionarTransaccionesAdminController {

    TransaccionController transaccionController = new TransaccionController();

    UsuarioController usuarioController = new UsuarioController();

    @FXML
    private TextField idTransaccion;
    @FXML
    private TextField idUsuario;
    @FXML
    private TextField tipoTransaccion;
    @FXML
    private TextField monto;
    @FXML
    private TextField descripcion;
    @FXML
    private TextField cuentaOrigen;
    @FXML
    private TextField cuentaDestino;


    @FXML
    private TableView<Transaccion> transaccionesTable;
    @FXML
    private TableColumn<Transaccion, String> tipoColumn;
    @FXML
    private TableColumn<Transaccion, Double> montoColumn;
    @FXML
    private TableColumn<Transaccion, LocalDateTime> fechaColumn;


    @FXML
    private void initialize() {
        // Configurar columnas de la tabla
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        montoColumn.setCellValueFactory(new PropertyValueFactory<>("monto"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        cargarTransacciones();
        // Cargar datos iniciales (si es necesario)
    }

    @FXML
    private void crearTransaccion() {
        // Validar campos obligatorios
        
        if (validarCampos()) {
            TransaccionDto nuevaTransaccion = construirTransaccion();
            transaccionController.agregarTransaccion(idUsuario.getText(), nuevaTransaccion);
            limpiarCampos();
            cargarTransacciones();
        } else {
            mostrarAlerta("Error", "Todos los campos (excepto Fecha) son obligatorios.");
        }
    }

    private TransaccionDto construirTransaccion() {
        CategoriaDto categoriaDto = new CategoriaDto("123", "Predeterminado", "Sin descripción");
        TransaccionDto transaccionDto = new TransaccionDto(
            idTransaccion.getText(),
            LocalDateTime.now(),
            tipoTransaccion.getText(),
            Double.parseDouble(monto.getText()),
            descripcion.getText(),
            cuentaOrigen.getText(),
            cuentaDestino.getText(),
            categoriaDto);
        return transaccionDto;
    }

    private boolean validarCampos() {
        return !idTransaccion.getText().isEmpty() &&
                !tipoTransaccion.getText().isEmpty() &&
                !monto.getText().isEmpty() &&
                !descripcion.getText().isEmpty() &&
                !cuentaOrigen.getText().isEmpty() &&
                !cuentaDestino.getText().isEmpty();
    }

    private void limpiarCampos() {
        idTransaccion.clear();
        tipoTransaccion.clear();
        monto.clear();
        descripcion.clear();
        cuentaOrigen.clear();
        cuentaDestino.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void cargarTransacciones() {
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        List<Transaccion> transacciones = new ArrayList<>();
    
        for (Usuario usuario : usuarios) {
            List<Transaccion> transaccionesUsuario = transaccionController.listarTransacciones(usuario.getIdUsuario());
            if (transaccionesUsuario != null) {
                transacciones.addAll(transaccionesUsuario);
            }
        }
    
        // Limpiar la tabla antes de agregar los nuevos datos
        transaccionesTable.getItems().clear();
    
        // Agregar todas las transacciones a la tabla
        transaccionesTable.getItems().addAll(transacciones);
    }
    
}
