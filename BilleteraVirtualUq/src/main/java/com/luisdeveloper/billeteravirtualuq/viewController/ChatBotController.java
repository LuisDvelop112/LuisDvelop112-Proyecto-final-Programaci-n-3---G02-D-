package com.luisdeveloper.billeteravirtualuq.viewController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatBotController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    private static final String API_TOKEN = "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"; // Reemplaza con tu token de Hugging Face
    private static final String API_URL = "https://api-inference.huggingface.co/models/gpt2"; // Cambia por el modelo que desees usar

    private final HttpClient httpClient;

    public ChatBotController() {
        httpClient = HttpClient.newHttpClient(); // Cliente HTTP reutilizable.
    }

    @FXML
    private void initialize() {
        sendButton.setOnAction(event -> procesarEntradaUsuario());
        inputField.setOnAction(event -> procesarEntradaUsuario());
    }

    @FXML
    private void procesarEntradaUsuario() {
        String userInput = inputField.getText().trim();
        if (!userInput.isEmpty()) {
            mostrarMensaje("Usuario: " + userInput);
            obtenerRespuestaDeAPI(userInput);
            inputField.clear();
        }
    }

    private void mostrarMensaje(String mensaje) {
        chatArea.appendText(mensaje + "\n");
    }

    private void obtenerRespuestaDeAPI(String userInput) {
        String json = """
                {
                    "inputs": "%s",
                    "parameters": {
                        "max_length": 200,
                        "temperature": 0.7
                    }
                }
                """.formatted(userInput);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_TOKEN)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::procesarRespuesta)
                .exceptionally(e -> {
                    mostrarMensajeEnInterfaz("Error: No se pudo conectar con el servidor.");
                    e.printStackTrace();
                    return null;
                });
    }

    private void procesarRespuesta(String jsonResponse) {
        try {
            System.out.println("Respuesta de la API: " + jsonResponse); // Diagnóstico para revisar la respuesta completa

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);

            // Extrae el texto generado del nodo "generated_text"
            String respuesta = rootNode.path(0).path("generated_text").asText();

            if (respuesta.isEmpty()) {
                mostrarMensajeEnInterfaz("Error: Respuesta vacía o incorrecta de la API.");
            } else {
                mostrarMensajeEnInterfaz("ChatBot: " + respuesta);
            }

        } catch (Exception e) {
            mostrarMensajeEnInterfaz("Error al procesar la respuesta del servidor.");
            e.printStackTrace();
        }
    }

    private void mostrarMensajeEnInterfaz(String mensaje) {
        javafx.application.Platform.runLater(() -> mostrarMensaje(mensaje));
    }
}
