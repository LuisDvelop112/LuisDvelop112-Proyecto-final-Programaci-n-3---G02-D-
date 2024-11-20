package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.luisdeveloper.billeteravirtualuq.controller.CategoriaController;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;

public class VistaCrearCategoriaController {

    @FXML
    private TextField id_categoria;

    @FXML
    private TextField nombre;

    @FXML
    private TextField descripcion;

    private CategoriaController categoriaController = new CategoriaController();

    @FXML
    private void crearCategoria(ActionEvent event) {
        // Validar entradas
        String id = id_categoria.getText().trim();
        String nombreCategoria = nombre.getText().trim();
        String descripcionCategoria = descripcion.getText().trim();

        if (id.isEmpty() || nombreCategoria.isEmpty() || descripcionCategoria.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos",
                    "Por favor, complete todos los campos para agregar la categoría.");
            return;
        }

        try {
            // Llamar al método del controlador para agregar la categoría
            boolean resultado = categoriaController.crearCategoria(crearCategoriaDto());

            if (resultado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                        "La categoría ha sido agregada correctamente.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error",
                        "No se pudo agregar la categoría. Verifique los datos ingresados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "Ocurrió un error al agregar la categoría: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String mensaje) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private CategoriaDto crearCategoriaDto(){
        return new CategoriaDto(id_categoria.getText(), nombre.getText(), descripcion.getText());
    }
}
