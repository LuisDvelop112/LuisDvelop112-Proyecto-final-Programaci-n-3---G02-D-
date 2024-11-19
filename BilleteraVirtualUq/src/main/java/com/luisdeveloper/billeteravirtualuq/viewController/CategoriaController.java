package com.luisdeveloper.billeteravirtualuq.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CategoriaController {

    // Inyectar los elementos de la interfaz FXML
    @FXML
    private TextField id_categoria;
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private Button agregarCategoriaButton;

    // Método para inicializar el controlador (si es necesario)
    @FXML
    private void initialize() {
        // Este método se ejecuta cuando se carga la vista
        agregarCategoriaButton.setOnAction(event -> agregarCategoria());
    }

    // Método para manejar el clic en el botón "Agregar Categoria"
    private void agregarCategoria() {
        // Obtener los valores de los campos
        String id = id_categoria.getText();
        String nombreCategoria = nombre.getText();
        String descripcionCategoria = descripcion.getText();

        // Validar que los campos no estén vacíos
        if (id.isEmpty() || nombreCategoria.isEmpty() || descripcionCategoria.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            return;
        }

        // Aquí puedes agregar la lógica para agregar la categoría (por ejemplo, guardarla en la base de datos)
        // Como ejemplo, simplemente imprimimos los datos en consola
        System.out.println("Categoria agregada:");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombreCategoria);
        System.out.println("Descripcion: " + descripcionCategoria);

        // Limpiar los campos después de agregar la categoría
        id_categoria.clear();
        nombre.clear();
        descripcion.clear();
    }
}
