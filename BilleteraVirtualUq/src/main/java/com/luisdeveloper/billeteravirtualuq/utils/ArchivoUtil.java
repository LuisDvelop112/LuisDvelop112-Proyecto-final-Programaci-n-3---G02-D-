package com.luisdeveloper.billeteravirtualuq.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ArchivoUtil {

    static String fechaSistema = "";

    // ------------------------------ Métodos para Archivos de Texto
    // ------------------------------

    /**
     * Este método recibe una cadena con el contenido que se quiere guardar en el
     * archivo.
     * 
     * @param ruta                es la ruta o path donde está ubicado el archivo
     * @param contenido           el contenido que se desea guardar
     * @param flagAnexarContenido indica si el contenido debe anexarse o
     *                            sobrescribir
     * @throws IOException si ocurre un error al guardar el archivo
     */
    public static void guardarArchivo(String ruta, String contenido, Boolean flagAnexarContenido) throws IOException {
        try (FileWriter fw = new FileWriter(ruta, flagAnexarContenido);
                BufferedWriter bfw = new BufferedWriter(fw)) {
            bfw.write(contenido);
        }
    }

    /**
     * Este método retorna el contenido de un archivo ubicado en una ruta como una
     * lista de cadenas.
     * 
     * @param ruta la ruta del archivo
     * @return una lista con las líneas del archivo
     * @throws IOException si ocurre un error al leer el archivo
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {
        ArrayList<String> contenido = new ArrayList<>();
        try (FileReader fr = new FileReader(ruta);
                BufferedReader bfr = new BufferedReader(fr)) {
            String linea;
            while ((linea = bfr.readLine()) != null) {
                contenido.add(linea);
            }
        }
        return contenido;
    }

    // ------------------------------ Métodos para Log de Registros
    // ------------------------------

    /**
     * Guarda un registro de log con el mensaje, nivel y acción indicados.
     * 
     * @param mensajeLog  el mensaje del log
     * @param nivel       el nivel del log (1: INFO, 2: WARNING, 3: SEVERE)
     * @param accion      la acción que se está registrando
     * @param rutaArchivo la ruta del archivo donde se guarda el log
     */
    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();

        try {
            fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                case 2:
                    LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                case 3:
                    LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                default:
                    break;
            }
        } catch (SecurityException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileHandler != null) {
                fileHandler.close();
            }
        }
    }

    /**
     * Carga la fecha actual del sistema.
     */
    private static void cargarFechaSistema() {
        Calendar cal1 = Calendar.getInstance();
        int dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH) + 1;
        int año = cal1.get(Calendar.YEAR);

        String diaN = (dia < 10) ? "0" + dia : String.valueOf(dia);
        String mesN = (mes < 10) ? "0" + mes : String.valueOf(mes);

        fechaSistema = año + "-" + mesN + "-" + diaN;
    }

    // ------------------------------ Métodos para Serialización
    // ------------------------------

    /**
     * Carga un objeto serializado desde un archivo.
     * 
     * @param rutaArchivo la ruta del archivo que contiene el objeto serializado
     * @return el objeto cargado
     * @throws Exception si ocurre un error al cargar el objeto
     */
    public static Object cargarRecursoSerializado(String rutaArchivo) throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return ois.readObject();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Guarda un objeto en un archivo utilizando serialización.
     * 
     * @param rutaArchivo la ruta del archivo donde se desea guardar el objeto
     * @param object      el objeto a serializar
     * @throws Exception si ocurre un error al guardar el objeto
     */
    public static void salvarRecursoSerializado(String rutaArchivo, Object object) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        }
    }

    // ------------------------------ Métodos para Serialización con XML
    // ------------------------------

    /**
     * Carga un objeto serializado desde un archivo XML.
     * 
     * @param rutaArchivo la ruta del archivo XML
     * @return el objeto cargado desde el XML
     * @throws IOException si ocurre un error al leer el archivo XML
     */
    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {
        try (XMLDecoder decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo))) {
            return decodificadorXML.readObject();
        }
    }

    /**
     * Guarda un objeto serializado en un archivo XML.
     * 
     * @param rutaArchivo la ruta del archivo XML donde se desea guardar el objeto
     * @param objeto      el objeto a serializar
     * @throws IOException si ocurre un error al escribir en el archivo XML
     */
    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {
        // Asegúrate de que el directorio de destino existe
        Path path = Paths.get(rutaArchivo);
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        try (XMLEncoder codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo))) {
            codificadorXML.writeObject(objeto);
        } catch (IOException e) {
            throw new IOException("Error al guardar el objeto en XML: " + e.getMessage(), e);
        }
    }

}
