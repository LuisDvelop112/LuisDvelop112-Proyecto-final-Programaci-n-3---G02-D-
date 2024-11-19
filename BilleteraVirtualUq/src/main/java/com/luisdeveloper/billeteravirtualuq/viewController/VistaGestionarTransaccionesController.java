package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.luisdeveloper.billeteravirtualuq.controller.TransaccionController;
import com.luisdeveloper.billeteravirtualuq.controller.UsuarioController;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;

import java.time.LocalDateTime;

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

    // Método para establecer el ID del usuario
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
        System.out.println("ID de usuario establecido: " + idUsuario);
    }

    // Inicialización del controlador
    @FXML
    public void initialize() {
        // Verificar que idUsuario no sea nulo antes de usarlo
        if (idUsuario != null) {
            UsuarioDto user = usuarioController.obtenerUsuario(idUsuario);

            if (user != null) {
                double saldo = usuarioController.obtenerSaldo(idUsuario);
                txtSaldoActual.setText(String.format("%.2f", saldo)); // Establecer el saldo
            }
        }
        System.out.println("VistaGestionarTransaccionesController inicializado correctamente.");
        cmbTipoTransaccion.getItems().addAll("Retiro", "Depósito", "Transferencia");
        cmbTipoTransaccion.getSelectionModel().selectFirst(); // Seleccionar el primero por defecto
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

            Double monto;
            // Crear la transacción basada en la entrada
            TransaccionDto nuevaTransaccion = new TransaccionDto(
                    txtPresupuestoId.getText(),
                    fechaHoraActual,
                    tipoTransaccion,
                    monto = Double.parseDouble(txtMontoPresupuesto.getText()),
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
            System.out.println("Transacción añadida correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El monto debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("Error al realizar la transacción: " + e.getMessage());
        }
    }

    // Método para manejar el botón "Eliminar"
    @FXML
    private void eliminarTransaccion() {
        System.out.println("Botón 'Eliminar' presionado.");
        // Lógica para eliminar transacción, si es necesario (ya no depende de la tabla)
    }

    // Método para limpiar los campos después de agregar una transacción
    private void limpiarCampos() {
        txtPresupuestoId.clear();
        txtMontoPresupuesto.clear();
        txtDescripcion.clear();
        txtcuentaOrigen.clear();
        txtcuentaDestino.clear();
    }

    @FXML
    private void agregarCategoria() {
        System.out.println("Botón 'Agregar Categoría' presionado.");
        // Lógica en desarrollo
    }

    public void setearSaldo(String idUsuario) {
        double saldo = usuarioController.obtenerSaldo(idUsuario);
        txtSaldoActual.setText(String.format("%.2f", saldo));
    }

}
