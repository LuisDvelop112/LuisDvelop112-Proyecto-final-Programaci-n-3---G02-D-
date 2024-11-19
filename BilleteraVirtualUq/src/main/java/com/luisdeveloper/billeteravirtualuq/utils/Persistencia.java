package com.luisdeveloper.billeteravirtualuq.utils;

import com.luisdeveloper.billeteravirtualuq.model.BilleteraVirtualUq;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class Persistencia {

    // Rutas de archivos base y específicos
    public static final String RUTA_BASE = "C:/td/persistencia/";
    public static final String RUTA_ARCHIVOS = RUTA_BASE + "archivos/";
    public static final String RUTA_RESPALDO = RUTA_BASE + "respaldo/";
    public static final String RUTA_LOG = RUTA_BASE + "log/";

    // Archivos de persistencia
    public static final String ARCHIVO_CUENTAS = RUTA_ARCHIVOS + "cuentas.txt";
    public static final String ARCHIVO_TRANSACCIONES = RUTA_ARCHIVOS + "Transaccion.txt";
    public static final String ARCHIVO_LOG = RUTA_LOG + "BilleteraVirtualUqLog.txt";

    // Archivos binarios y XML del modelo
    public static final String RUTA_ARCHIVO_MODELO_BINARIO = RUTA_BASE + "model.dat";
    public static final String RUTA_ARCHIVO_MODELO_XML = "C:/td/Persistencia/model.xml";

    // ------------------------------ Respaldos ------------------------------

    /**
     * Realiza un respaldo de los archivos de modelo en formato binario y XML.
     *
     * @param billetera objeto BilleteraVirtualUq que contiene los datos.
     */
    public static void respaldarArchivos(BilleteraVirtualUq billetera) {

        // Crear la carpeta de respaldo si no existe
        if (billetera != null) {
            try {
                Files.createDirectories(Paths.get(RUTA_RESPALDO));

                // Respaldar el modelo a archivo binario
                String nombreBinarioRespaldo = "model_backup_" + obtenerFechaHora() + ".dat";
                Path archivoBinarioRespaldo = Paths.get(RUTA_RESPALDO + nombreBinarioRespaldo);
                ArchivoUtil.salvarRecursoSerializado(archivoBinarioRespaldo.toString(), billetera);
                System.out.println("Respaldo Binario creado: " + nombreBinarioRespaldo);

                // Respaldar el modelo a archivo XML
                String nombreXmlRespaldo = "model_backup_" + obtenerFechaHora() + ".xml";
                Path archivoXmlRespaldo = Paths.get(RUTA_RESPALDO + nombreXmlRespaldo);
                ArchivoUtil.salvarRecursoSerializadoXML(archivoXmlRespaldo.toString(), billetera);
                System.out.println("Respaldo XML creado: " + nombreXmlRespaldo);

                System.out.println("Archivos respaldados correctamente.");
            } catch (Exception e) {
                System.out.println("Error al respaldar archivos: " + e.getMessage());
            }
        }else{
            System.out.println("Error al guardar");
        }

    }

    // ------------------------------ Guardado y carga de datos
    // ------------------------------

    /**
     * Guarda la lista de cuentas en el archivo correspondiente.
     * 
     * @param listaCuentas la lista de cuentas a guardar.
     * @throws IOException si ocurre un error al guardar las cuentas.
     */
    public static void guardarCuentas(List<Cuenta> listaCuentas) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Cuenta cuenta : listaCuentas) {
            contenido.append(cuenta.getIdCuenta()).append("@@")
                    .append(cuenta.getNombreBanco()).append("@@")
                    .append(cuenta.getNumeroCuenta()).append("@@")
                    .append(cuenta.getTipoCuenta()).append("\n");
        }
        ArchivoUtil.guardarArchivo(ARCHIVO_CUENTAS, contenido.toString(), false);
    }

    // ------------------------------ Respaldo de Transacciones
    // ------------------------------

    /**
     * Realiza un respaldo de las transacciones a un archivo.
     *
     * @throws IOException si ocurre un error al realizar el respaldo.
     */
    @SuppressWarnings("unused")
    private static void respaldarTransacciones() throws IOException {
        Path origen = Paths.get(ARCHIVO_TRANSACCIONES);
        String nombreRespaldo = "backup_" + obtenerFechaHora() + ".txt";
        Path destino = Paths.get(RUTA_RESPALDO + nombreRespaldo);
        Files.createDirectories(destino.getParent());
        Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Obtiene la fecha y hora actual en formato `ddMMyyyy_HHmmss`.
     *
     * @return la fecha y hora formateada.
     */
    private static String obtenerFechaHora() {
        return new java.text.SimpleDateFormat("ddMMyyyy_HHmmss").format(new java.util.Date());
    }

    // ------------------------------ Registro de Logs
    // ------------------------------

    /**
     * Guarda un registro de log en el archivo correspondiente.
     *
     * @param mensajeLog el mensaje del log
     * @param nivel      el nivel del log (1: INFO, 2: WARNING, 3: SEVERE)
     * @param accion     la acción que se está registrando
     */
    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, ARCHIVO_LOG);
    }

    // ------------------------------ Guardado y carga de recursos binarios y XML
    // ------------------------------

    /**
     * Carga el recurso binario desde el archivo.
     *
     * @return el objeto BilleteraVirtualUq cargado desde el archivo binario
     * @throws Exception si ocurre un error al cargar el archivo
     */
    public static BilleteraVirtualUq cargarRecursoBinario() throws Exception {
        return (BilleteraVirtualUq) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BINARIO);
    }

    /**
     * Guarda el recurso binario en el archivo.
     *
     * @param billeteraVirtualUq el objeto a guardar
     * @throws Exception si ocurre un error al guardar el archivo
     */
    public static void guardarRecursoBinario(BilleteraVirtualUq billeteraVirtualUq) throws Exception {
        ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BINARIO, billeteraVirtualUq);
    }

    /**
     * Carga el recurso XML desde el archivo.
     *
     * @return el objeto BilleteraVirtualUq cargado desde el archivo XML
     * @throws IOException si ocurre un error al cargar el archivo XML
     */
    public static BilleteraVirtualUq cargarRecursoXML() throws IOException {
        return (BilleteraVirtualUq) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_XML);
    }

    /**
     * Guarda el recurso XML en el archivo.
     *
     * @param billeteraVirtualUq el objeto a guardar
     * @throws IOException si ocurre un error al guardar el archivo XML
     */
    public static void guardarRecursoXML(BilleteraVirtualUq billeteraVirtualUq) throws IOException {
        ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_XML, billeteraVirtualUq);
    }
}
