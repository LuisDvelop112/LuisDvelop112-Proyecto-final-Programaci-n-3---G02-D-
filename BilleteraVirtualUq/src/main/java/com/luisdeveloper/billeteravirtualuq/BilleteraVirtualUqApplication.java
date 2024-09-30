package com.luisdeveloper.billeteravirtualuq;

import com.luisdeveloper.billeteravirtualuq.viewController.CuentaViewController;

import javafx.application.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BilleteraVirtualUqApplication extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("BILLETERA VIRTUAL UQ");
        mostrarVentanaPrincipal();
    }

    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();

            // Verifica la ubicación del archivo FXML
            URL fxmlLocation = getClass().getResource("CuentaView.fxml");
            if (fxmlLocation == null) {
                System.err.println("Error: El archivo FXML no se encontró en la ruta especificada.");
                return; // Salimos si no se encuentra el archivo
            }

            loader.setLocation(fxmlLocation);

            // Cargar el archivo FXML
            AnchorPane rootLayout = (AnchorPane) loader.load();

            // Obtener el controlador y configurar la aplicación si es necesario
          //  CuentaViewController cuentaViewController = loader.getController();
            // cuentaViewController.setAplicacion(this);

            // Mostrar la escena en la ventana principal
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}