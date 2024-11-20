package com.luisdeveloper.billeteravirtualuq.utils;

import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMQUtils {

    private static Connection connection;
    private static Channel channel;

    private static final String HOST = "localhost"; // Cambiar si es necesario
    private static final int PORT = 5672; // Puerto de RabbitMQ
    private static final String USERNAME = "guest"; // Usuario de RabbitMQ
    private static final String PASSWORD = "guest"; // Contraseña de RabbitMQ

    static {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con RabbitMQ: " + e.getMessage());
        }
    }

    public static Channel getChannel() {
        return channel;
    }

    public static void close() {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para enviar una nueva transacción a RabbitMQ
    public static void enviarTransaccionARabbitMQ(TransaccionDto transaccionActualizada) {
        try {
            // Convertir la transacción a JSON o una cadena representativa
            String mensaje = convertirTransaccionAJSON(transaccionActualizada);

            // Publicar en la cola "transacciones"
            channel.basicPublish("", "transacciones", null, mensaje.getBytes());
            System.out.println("Transacción enviada a RabbitMQ: " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar información de eliminación de una transacción a RabbitMQ
    public static void enviarEliminacionTransaccionARabbitMQ(String idUsuario, String idTransaccion) {
        try {
            // Crear un mensaje que indique que se eliminó la transacción
            String mensaje = String.format("Eliminación de transacción: Usuario: %s, ID: %s", idUsuario, idTransaccion);

            // Publicar en la cola "transaccionesEliminadas"
            channel.basicPublish("", "transaccionesEliminadas", null, mensaje.getBytes());
            System.out.println("Eliminación de transacción enviada a RabbitMQ: " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para convertir una transacción a JSON o una cadena (ejemplo de implementación)
    private static String convertirTransaccionAJSON(TransaccionDto transaccion) {
        // Aquí puedes usar una librería como Jackson o Gson para convertir a JSON
        // Este es solo un ejemplo simple de cómo podrías formatear la transacción como cadena
        return String.format("Transacción ID: %s, Usuario: %s, Monto: %s, Tipo: %s", 
                                transaccion.idTransaccion(), transaccion.descripcion(), transaccion.monto(), transaccion.categoria().nombre());
    }

    // Método para iniciar un consumidor para recibir las transacciones
    public static void iniciarConsumidorTransacciones() {
        try {
            // Declarar la cola donde se recibirán las transacciones
            channel.queueDeclare("transacciones", false, false, false, null);

            // Definir el comportamiento cuando se reciba un mensaje de RabbitMQ
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String mensaje = new String(delivery.getBody(), "UTF-8");
                System.out.println("Mensaje de transacción recibido: " + mensaje);

                // Aquí puedes procesar la transacción
                procesarTransaccion(mensaje);
            };

            // Iniciar el consumo de mensajes de la cola "transacciones"
            channel.basicConsume("transacciones", true, deliverCallback, consumerTag -> { });
            System.out.println("Consumidor de transacciones iniciado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para iniciar un consumidor para recibir las eliminaciones de transacciones
    public static void iniciarConsumidorEliminaciones() {
        try {
            // Declarar la cola donde se recibirán las eliminaciones de transacciones
            channel.queueDeclare("transaccionesEliminadas", false, false, false, null);

            // Definir el comportamiento cuando se reciba un mensaje de RabbitMQ
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String mensaje = new String(delivery.getBody(), "UTF-8");
                System.out.println("Mensaje de eliminación recibido: " + mensaje);

                // Aquí puedes procesar la eliminación
                procesarEliminacion(mensaje);
            };

            // Iniciar el consumo de mensajes de la cola "transaccionesEliminadas"
            channel.basicConsume("transaccionesEliminadas", true, deliverCallback, consumerTag -> { });
            System.out.println("Consumidor de eliminaciones iniciado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para procesar una transacción recibida (simulación de procesamiento)
    private static void procesarTransaccion(String mensaje) {
        // Aquí puedes implementar la lógica para procesar la transacción recibida
        System.out.println("Procesando transacción: " + mensaje);
    }

    // Método para procesar una eliminación de transacción recibida (simulación de procesamiento)
    private static void procesarEliminacion(String mensaje) {
        // Aquí puedes implementar la lógica para procesar la eliminación de la transacción recibida
        System.out.println("Procesando eliminación: " + mensaje);
    }
}
