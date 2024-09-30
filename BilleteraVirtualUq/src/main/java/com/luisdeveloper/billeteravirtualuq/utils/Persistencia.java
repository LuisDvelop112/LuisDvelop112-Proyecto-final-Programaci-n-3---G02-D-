package com.luisdeveloper.billeteravirtualuq.utils;

import com.luisdeveloper.billeteravirtualuq.model.Cuenta;
import com.luisdeveloper.billeteravirtualuq.model.BilleteraVirtualUq;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {

    public static final String RUTA_ARCHIVO_CUENTAS = "src/main/resources/Persistencia/archivoCuentas.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/Persistencia/Log/BilleteraVirtualUqLog.txt";

    //public static final String RUTA_ARCHIVO_OBJETOS = "co.edu.uniquindio.programacion3/src/main/resources/persistencia/archivoObjetos.txt";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERAVIRTUALUQ_BINARIO = "src/main/resources/Persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERAVIRTUALUQ_XML = "src/main/resources/Persistencia/model.xml";
//	C:\td\persistencia



    public static void cargarDatosArchivos(BilleteraVirtualUq billeteraVirtualUq) throws FileNotFoundException, IOException {
        //cargar archivos empleados
        ArrayList<Cuenta> cuentasCargados = cargarCuentas();
        if(cuentasCargados.size() > 0)
            billeteraVirtualUq.getListaCuentas().addAll(cuentasCargados);
    }

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param
     * @throws IOException
     */
    public static void guardarCuentas(ArrayList<Cuenta> listaCuentas) throws IOException {
        String contenido = "";
        for(Cuenta cuenta:listaCuentas)
        {
            contenido+= cuenta.getIdCuenta()+
                    ","+cuenta.getNombreBanco()+
                    ","+cuenta.getNumeroCuenta()+
                    ","+cuenta.getTipoCuenta()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_CUENTAS, contenido, false);
    }


//	----------------------LOADS------------------------

    /**
     *
     * @param
     * @param
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<Cuenta> cargarCuentas() throws FileNotFoundException, IOException {
        ArrayList<Cuenta> cuentas =new ArrayList<Cuenta>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_CUENTAS);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);
            Cuenta cuenta = new Cuenta();
            cuenta.setIdCuenta(linea.split(",")[0]);
            cuenta.setNombreBanco(linea.split(",")[1]);
            cuenta.setNumeroCuenta(linea.split(",")[2]);
            cuenta.setTipoCuenta(linea.split(",")[3]);
            cuentas.add(cuenta);
        }
        return cuentas;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
    {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }


//	----------------------SAVES------------------------

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param ruta
     * @throws IOException
     */

    public static void guardarObjetos(ArrayList<Cuenta> listaCuentas, String ruta) throws IOException  {
        String contenido = "";

        for(Cuenta CuentaAux:listaCuentas) {
            contenido+= CuentaAux.getIdCuenta()+","+CuentaAux.getNombreBanco()+","+CuentaAux.getNumeroCuenta()+","+CuentaAux.getTipoCuenta()+"\n";
        }
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }


    //------------------------------------SERIALIZACIÓN  y XML


    public static BilleteraVirtualUq cargarRecursoBilleteraVirtualUqBinario() {

        BilleteraVirtualUq billeteraVirtualUq = null;

        try {
            billeteraVirtualUq = (BilleteraVirtualUq)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUALUQ_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return billeteraVirtualUq;
    }

    public static void guardarRecursosBilleteraVirtualUqBinario(BilleteraVirtualUq billeteraVirtualUq) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUALUQ_BINARIO, billeteraVirtualUq);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static BilleteraVirtualUq cargarRecursosBilleteraVirtualUqXML() {

        BilleteraVirtualUq billeteraVirtualUq = null;

        try {
            billeteraVirtualUq = (BilleteraVirtualUq)ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUALUQ_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return billeteraVirtualUq;

    }


    public static void guardarRecursoBilleteraVirtualUqXML(BilleteraVirtualUq billeteraVirtualUq) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUALUQ_XML, billeteraVirtualUq);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
