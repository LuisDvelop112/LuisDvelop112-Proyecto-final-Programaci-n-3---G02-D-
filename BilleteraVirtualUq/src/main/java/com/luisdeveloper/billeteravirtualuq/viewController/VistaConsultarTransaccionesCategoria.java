package com.luisdeveloper.billeteravirtualuq.viewController;

import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.TransaccionController;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaConsultarTransaccionesCategoria {

    // Controlador para manejar las transacciones
    TransaccionController transaccionController = new TransaccionController();
    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo o vacío.");
        }
        this.idUsuario = idUsuario;
    }

    @FXML
    private TableView<Transaccion> tableTransacciones;

    @FXML
    private TableColumn<Transaccion, String> tcFecha;

    @FXML
    private TableColumn<Transaccion, String> tcTipo;

    @FXML
    private TableColumn<Transaccion, String> tcCategoria;

    @FXML
    private TableColumn<Transaccion, Double> tcMonto;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private ComboBox<String> comboCategoria;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    private TextField txtFechaFin;

    @FXML
    private Button btnFiltrar;

    // Método para cargar las transacciones en la tabla
    public void cargarTransacciones() {
        List<Transaccion> transacciones = transaccionController.listarTransacciones(idUsuario);

        // Limpiar la tabla antes de llenarla
        tableTransacciones.getItems().clear();

        // Configurar las columnas de la tabla
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tcMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        // Añadir las transacciones a la tabla
        tableTransacciones.getItems().addAll(transacciones);
    }

    // Método para manejar el filtro de las transacciones
    @FXML
    private void filtrarFecha() {
        try {
            // Obtener los valores de los filtros
            String tipoFiltro = comboTipo.getValue();
            String categoriaFiltro = comboCategoria.getValue();
            String fechaInicio = txtFechaInicio.getText();
            String fechaFin = txtFechaFin.getText();

            // Filtrar las transacciones basadas en los filtros seleccionados
            List<Transaccion> transaccionesFiltradas = null;

            // Limpiar la tabla antes de llenarla con las transacciones filtradas
            tableTransacciones.getItems().clear();

            // Cargar las transacciones filtradas en la tabla
            tableTransacciones.getItems().addAll(transaccionesFiltradas);

        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al filtrar las transacciones. Verifique los filtros ingresados.");
        }
    }
    @FXML
    private void filtrarTipo(){

    }

    @FXML
    private void filtrarCategoria(){
        
    }

    // Método para mostrar alertas
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para manejar el click en una transacción de la tabla (opcional)
    @FXML
    private void manejarClickTabla(MouseEvent event) {
        // Obtener la transacción seleccionada
        Transaccion transaccionSeleccionada = tableTransacciones.getSelectionModel().getSelectedItem();

        // Si se selecciona una transacción, mostrar información adicional (si es necesario)
        if (transaccionSeleccionada != null) {
            System.out.println("Transacción seleccionada: " + transaccionSeleccionada);
        }
    }

    // Método para inicializar los filtros
    public void inicializarFiltros() {
        // Rellenar los ComboBox con las opciones de tipo y categoría (ejemplo)
        comboTipo.getItems().addAll("Ingreso", "Egreso");
        comboCategoria.getItems().addAll("Alimentos", "Transporte", "Entretenimiento", "Salud", "Varios");

        // Establecer valores predeterminados si es necesario
        comboTipo.setValue("Ingreso");
        comboCategoria.setValue("Alimentos");
    }
}
