package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.luisdeveloper.billeteravirtualuq.controller.TransaccionController;
import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import java.time.LocalDateTime;
import java.util.List;

public class VistaGestionarTransaccionesController {

    private final TransaccionController transaccionController = new TransaccionController();
    private final UsuarioController usuarioController = new UsuarioController();

    private String idUsuario;

    // Campos FXML
    @FXML
    private ComboBox<String> cmbTipoTransaccion;
    @FXML
    private TextField txtSaldoActual;
    @FXML
    private TextField txtPresupuestoId;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtMontoPresupuesto;
    @FXML
    private TextField txtcuentaOrigen;
    @FXML
    private TextField txtcuentaDestino;

    @FXML
    private TableView<Transaccion> tableTransacciones; // Tabla de transacciones
    @FXML
    private TableColumn<Transaccion, String> columTipoTransaccion;
    @FXML
    private TableColumn<Transaccion, String> columFecha;
    @FXML
    private TableColumn<Transaccion, Double> columMonto;

    // Método para establecer el ID del usuario
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        System.out.println("ID de usuario establecido: " + idUsuario);
    }

    // Inicialización del controlador
    @FXML
    public void initialize() {

        tableTransacciones.getItems().clear();

        columTipoTransaccion.setCellValueFactory(new PropertyValueFactory<>("tipoTransaccion"));
        columFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        cmbTipoTransaccion.getItems().addAll("Retiro", "Depósito", "Transferencia");
        cmbTipoTransaccion.getSelectionModel().selectFirst();

        
        List<Transaccion> transacciones = transaccionController.listarTransacciones(idUsuario);
        if(transacciones != null){
            tableTransacciones.getItems().addAll(transacciones);
        }

        tableTransacciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarTransacciones();
            }
        });
    }

    public void cargarTransacciones() {
        if (idUsuario != null) {
            // Obtener las transacciones directamente desde el controlador
            List<Transaccion> transacciones = transaccionController.listarTransacciones(idUsuario);

            if (transacciones != null) {
                // Añadir las transacciones a la tabla cuando estén disponibles
                tableTransacciones.getItems().setAll(transacciones);
            }
        }
    }

    // Método para manejar el botón "Realizar transacción"
    @FXML
    private void realizarTransaccion() {
        System.out.println("Botón 'Realizar transacción' presionado.");

        CategoriaDto categoriaDto = new CategoriaDto("123", "Predeterminado", "Sin descripción");
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        try {
            // Validar el tipo de transacción seleccionado
            String tipoTransaccion = cmbTipoTransaccion.getValue(); // Cambiado a `getValue()`
            if (tipoTransaccion == null || tipoTransaccion.isEmpty()) {
                System.out.println("Error: Debe seleccionar un tipo de transacción.");
                return;
            }

            Double monto = Double.parseDouble(txtMontoPresupuesto.getText());

            // Crear la transacción basada en la entrada
            TransaccionDto nuevaTransaccion = new TransaccionDto(
                    txtPresupuestoId.getText(),
                    fechaHoraActual,
                    tipoTransaccion,
                    monto,
                    txtDescripcion.getText(),
                    txtcuentaOrigen.getText(),
                    txtcuentaDestino.getText(),
                    categoriaDto);

            // Evaluar el tipo de transacción
            switch (tipoTransaccion) {
                case "Retiro":
                    usuarioController.reducirSaldo(idUsuario, monto);
                    break;
                case "Depósito":
                    usuarioController.aumentarSaldo(idUsuario, monto);
                    break;
                case "Transferencia":
                    usuarioController.reducirSaldo(idUsuario, monto);
                    break;
                default:
                    System.out.println("Error: Tipo de transacción no reconocido.");
                    return;
            }

            // Guardar la transacción
            transaccionController.agregarTransaccion(idUsuario, nuevaTransaccion);

            // Limpiar campos después de agregar la transacción
            limpiarCampos();

            // Recargar las transacciones en la tabla
            cargarTransacciones();

            setearSaldo(idUsuario);

            System.out.println("Transacción añadida correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El monto debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("Error al realizar la transacción: " + e.getMessage());
        }
    }

    // Método para limpiar los campos después de agregar una transacción
    private void limpiarCampos() {
        txtPresupuestoId.clear();
        txtMontoPresupuesto.clear();
        txtDescripcion.clear();
        txtcuentaOrigen.clear();
        txtcuentaDestino.clear();
    }

    public void setearSaldo(String idUsuario) {
        double saldo = usuarioController.obtenerSaldo(idUsuario);
        txtSaldoActual.setText(String.format("%.2f", saldo));
    }
}
