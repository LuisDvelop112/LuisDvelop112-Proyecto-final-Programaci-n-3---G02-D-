package com.luisdeveloper.billeteravirtualuq.viewController;

import com.luisdeveloper.billeteravirtualuq.controller.TransaccionController;
import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class VistaDetalleTransaccionesController {

    TransaccionController transaccionController = new TransaccionController();
    UsuarioController usuarioController = new UsuarioController();
    String idUsuario;

    // Método para setear el idUsuario
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    private TextField txtSaldo;
    @FXML
    private TableView<Transaccion> tablaTransacciones;
    @FXML
    private TableColumn<Transaccion, String> columnaFecha;
    @FXML
    private TableColumn<Transaccion, Double> columnaMonto;
    @FXML
    private TableColumn<Transaccion, String> columnaDescripcion;

    // Método para cargar las transacciones en la tabla
    public void cargarTransacciones() {
        
        setearSaldo();
        // Obtener todas las transacciones del usuario
        List<Transaccion> transacciones = transaccionController.listarTransacciones(idUsuario);

        // Limpiar la tabla antes de llenarla
        tablaTransacciones.getItems().clear();

        // Cargar las columnas de la tabla con los valores
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Añadir los datos a la tabla
        tablaTransacciones.getItems().addAll(transacciones);

        System.out.println("Transacciones cargadas correctamente.");
    }

    public void setearSaldo(){
        double saldo = usuarioController.obtenerSaldo(idUsuario);
        txtSaldo.setText(String.format("%.2f", saldo)); 
    }
}
