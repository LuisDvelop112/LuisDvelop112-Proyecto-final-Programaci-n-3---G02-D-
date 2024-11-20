package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class VistaReportesController {

    @FXML
    private CheckBox cbTransacciones;

    @FXML
    private CheckBox cbPresupuestos;

    @FXML
    private CheckBox cbCuentas;

    @FXML
    private void generarReporte(ActionEvent event) {
        boolean seleccionTransacciones = cbTransacciones.isSelected();
        boolean seleccionPresupuestos = cbPresupuestos.isSelected();
        boolean seleccionCuentas = cbCuentas.isSelected();

        if (!seleccionTransacciones && !seleccionPresupuestos && !seleccionCuentas) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Vacía",
                    "Por favor, seleccione al menos una opción para generar el reporte.");
            return;
        }

        // Generar los reportes seleccionados
        if (seleccionTransacciones) {
            generarReporteTransacciones();
        }
        if (seleccionPresupuestos) {
            generarReportePresupuestos();
        }
        if (seleccionCuentas) {
            generarReporteCuentas();
        }

        mostrarAlerta(Alert.AlertType.INFORMATION, "Reportes Generados",
                "Los reportes seleccionados han sido generados correctamente.");
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cierra la ventana actual
        Stage stage = (Stage) cbTransacciones.getScene().getWindow();
        stage.close();
    }

    private void generarReporteTransacciones() {
        // Lógica para generar reporte de transacciones
        System.out.println("Reporte de transacciones generado.");
    }

    private void generarReportePresupuestos() {
        // Lógica para generar reporte de presupuestos
        System.out.println("Reporte de presupuestos generado.");
    }

    private void generarReporteCuentas() {
        // Lógica para generar reporte de cuentas
        System.out.println("Reporte de cuentas generado.");
    }

    private void mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String mensaje) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
