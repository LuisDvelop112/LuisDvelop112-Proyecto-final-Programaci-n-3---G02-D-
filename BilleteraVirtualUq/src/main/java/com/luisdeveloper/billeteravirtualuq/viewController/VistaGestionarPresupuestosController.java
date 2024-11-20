package com.luisdeveloper.billeteravirtualuq.viewController;

import java.util.List;

import com.luisdeveloper.billeteravirtualuq.controller.CategoriaController;
import com.luisdeveloper.billeteravirtualuq.controller.PresupuestoController;
import com.luisdeveloper.billeteravirtualuq.model.Categoria;
import com.luisdeveloper.billeteravirtualuq.model.Presupuesto;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class VistaGestionarPresupuestosController {

    PresupuestoController presupuestoController = new PresupuestoController();
    CategoriaController categoriaController = new CategoriaController();

    String idUsuario;

    public void setIdUsuario(String idUsuario) {
        if (idUsuario == null || idUsuario.isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo o vacío.");
        }
        this.idUsuario = idUsuario;
    }

    @FXML
    private TextField PresupuestoId;

    @FXML
    private TextField NombrePresupuesto;

    @FXML
    private TextField CategoriaPresupuesto;

    @FXML
    private TextField MontoPresupuesto;

    @FXML
    private TextField MontoPresupuestoGastado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<Presupuesto> tablePresupuestos;

    @FXML
    private TableColumn<Presupuesto, String> tcPresupuestoId;

    @FXML
    private TableColumn<Presupuesto, String> tcNombre;

    @FXML
    private TableColumn<Presupuesto, String> tcCategoria;

    @FXML
    private TableColumn<Presupuesto, Double> tcMonto;

    @FXML
    private TableColumn<Presupuesto, Double> tcMontoGastado;

    // Acción para el botón "Nuevo"
    @FXML
    private void nuevoPresupuestoAction() {
        // Limpiar los campos de texto
        PresupuestoId.clear();
        NombrePresupuesto.clear();
        CategoriaPresupuesto.clear();
        MontoPresupuesto.clear();
        MontoPresupuestoGastado.clear();

        // Mostrar mensaje de información
        mostrarMensaje("Nuevo Presupuesto", "Los campos han sido limpiados para agregar un nuevo presupuesto.");
    }

    // Acción para el botón "Agregar"
    @FXML
    private void agregarPresupuestoAction() {
        try {
            // Validar que los campos no estén vacíos
            if (PresupuestoId.getText().isEmpty() || NombrePresupuesto.getText().isEmpty() || 
                CategoriaPresupuesto.getText().isEmpty() || MontoPresupuesto.getText().isEmpty()) {
                mostrarMensaje("Error", "Todos los campos deben ser llenados.");
                return;
            }

            // Obtener categoría y verificar monto
            Categoria Cat = categoriaController.buscarCategoria(CategoriaPresupuesto.getText());
            double monto = Double.parseDouble(MontoPresupuesto.getText());
            double montoGastado = Double.parseDouble(MontoPresupuestoGastado.getText());
            if (monto <= 0) {
                mostrarMensaje("Error", "El monto debe ser mayor a cero.");
                return;
            }

            // Crear nuevo presupuesto y agregarlo
            Presupuesto presupuesto = new Presupuesto(PresupuestoId.getText(), NombrePresupuesto.getText(), monto,montoGastado , Cat);
            presupuestoController.agregarPresupuesto(idUsuario, presupuesto);
            mostrarMensaje("Éxito", "Presupuesto agregado correctamente.");
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Por favor ingrese un valor numérico válido para el monto.");
        }
        cargarTablaPresupuestos();
    }

    // Acción para el botón "Actualizar"
    @FXML
    private void actualizarPresupuestoAction() {
        try {
            if (PresupuestoId.getText().isEmpty() || MontoPresupuestoGastado.getText().isEmpty()) {
                mostrarMensaje("Error", "Los campos no pueden estar vacíos.");
                return;
            }

            // Obtener el presupuesto y validar el monto gastado
            Presupuesto presu = presupuestoController.buscarPresupuesto(idUsuario, PresupuestoId.getText());
            if (presu == null) {
                mostrarMensaje("Error", "El presupuesto no existe.");
                return;
            }

            double montoTotal = presu.getMontoTotal();
            double montoGastado = Double.parseDouble(MontoPresupuestoGastado.getText());

            if (montoGastado > montoTotal) {
                mostrarMensaje("Saldo Excedido", "El saldo gastado no puede ser mayor que el monto total.");
                return;
            }

            // Actualizar presupuesto
            Categoria Cat = categoriaController.buscarCategoria(CategoriaPresupuesto.getText());
            Presupuesto presupuesto = new Presupuesto(PresupuestoId.getText(), NombrePresupuesto.getText(),
                    Double.parseDouble(MontoPresupuesto.getText()), montoGastado, Cat);
            presupuestoController.actualizarPresupuesto(idUsuario, PresupuestoId.getText(), presupuesto);
            mostrarMensaje("Actualización completada", "Se ha actualizado con éxito.");
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Por favor ingrese un valor numérico válido para el monto gastado.");
        }
        cargarTablaPresupuestos();
    }

    // Acción para el botón "Eliminar"
    @FXML
    private void eliminarPresupuestoAction() {
        presupuestoController.eliminarPresupuesto(idUsuario, PresupuestoId.getText());
        mostrarMensaje("Eliminación completada", "Presupuesto eliminado correctamente.");
    }

    // Método para mostrar alertas
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para cargar los presupuestos en la tabla

    // Método para cargar los datos cuando se hace clic en una fila de la tabla
    @FXML
    private void manejarClickTabla(MouseEvent event) {
        // Obtener el presupuesto seleccionado
        Presupuesto presupuestoSeleccionado = tablePresupuestos.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un presupuesto
        if (presupuestoSeleccionado != null) {
            // Cargar los datos del presupuesto en los campos de texto
            PresupuestoId.setText(presupuestoSeleccionado.getIdPresupuesto());
            NombrePresupuesto.setText(presupuestoSeleccionado.getNombre());
            CategoriaPresupuesto.setText(presupuestoSeleccionado.getCategoria().getNombre());
            MontoPresupuesto.setText(String.valueOf(presupuestoSeleccionado.getMontoTotal()));
            MontoPresupuestoGastado.setText(String.valueOf(presupuestoSeleccionado.getMontoGastado()));
        }
    }

    public void cargarTablaPresupuestos() {
        // Obtener todos los presupuestos del usuario
        List<Presupuesto> presupuestos = presupuestoController.devolverPresupuestos(idUsuario);
    
        // Verificar si la lista de presupuestos es nula o vacía
        if (presupuestos == null || presupuestos.isEmpty()) {
            System.out.println("No hay presupuestos disponibles.");
            return;  // Salir del método si no hay presupuestos para mostrar
        }
    
        // Limpiar la tabla antes de llenarla
        tablePresupuestos.getItems().clear();
        
        // Verificar si el primer presupuesto tiene datos válidos
        if (presupuestos.get(0) != null) {
            System.out.println(presupuestos.get(0).getMontoGastado());
        }
    
        // Configurar las columnas de la tabla
        tcPresupuestoId.setCellValueFactory(new PropertyValueFactory<>("idPresupuesto"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tcMonto.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        tcMontoGastado.setCellValueFactory(new PropertyValueFactory<>("montoGastado"));
    
        // Añadir los datos a la tabla
        tablePresupuestos.getItems().addAll(presupuestos);
        System.out.println("Presupuestos cargados correctamente.");
    }
    
}
