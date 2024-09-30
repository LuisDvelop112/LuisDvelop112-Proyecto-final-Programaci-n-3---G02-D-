package com.luisdeveloper.billeteravirtualuq.controller;

import com.luisdeveloper.billeteravirtualuq.controller.services.IModelFactoryService;
import com.luisdeveloper.billeteravirtualuq.exceptions.*;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.mappers.BilleteraVirtualUqMapper;
import com.luisdeveloper.billeteravirtualuq.model.*;
import com.luisdeveloper.billeteravirtualuq.utils.Persistencia;
import com.luisdeveloper.billeteravirtualuq.utils.BilleteraVirtualUqUtils;

import java.io.IOException;
import java.util.List;

public class ModelFactoryController implements IModelFactoryService {

    BilleteraVirtualUq billeteraVirtualUq;
    BilleteraVirtualUqMapper mapper = BilleteraVirtualUqMapper.INSTANCE;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        //1. inicializar datos y luego guardarlo en archivos
        System.out.println("invocación clase singleton");
        cargarDatosBase();
        salvarDatosPrueba();

        //Siempre se debe verificar si la raiz del recurso es null

        if(billeteraVirtualUq == null){
            cargarDatosBase();
            guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");
    }

    private void cargarDatosBase() {
        billeteraVirtualUq = BilleteraVirtualUqUtils.inicializarDatos();

    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarCuentas(getBilleteraVirtualUq().getListaCuentas());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BilleteraVirtualUq getBilleteraVirtualUq() {
        return billeteraVirtualUq;
    }

    public void setBilleteraVirtualUq(BilleteraVirtualUq billeteraVirtualUq) {
        this.billeteraVirtualUq = billeteraVirtualUq;
    }


    @Override
    public List<CuentaDto> obtenerCuentas() {
        return  mapper.getCuentasDto(billeteraVirtualUq.getListaCuentas());
    }

    @Override
    public boolean agregarCuenta(CuentaDto cuentaDto) {
        try{
            if(!billeteraVirtualUq.verificarCuentaExistente(cuentaDto.idCuenta())) {
                Cuenta cuenta = mapper.cuentaDtoToCuenta(cuentaDto);
                getBilleteraVirtualUq().agregarEmpleado(cuenta);
                registrarAccionesSistema("Se agrego la cuenta "+ cuenta.getIdCuenta(),1,"agregarCuenta");
                guardarResourceXML();
            }
            return true;
        }catch (CuentaException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarCuenta(String idCuenta) {
        boolean flagExiste = false;
        try {
            flagExiste = getBilleteraVirtualUq().eliminarCuenta(idCuenta);
            registrarAccionesSistema("Se elimino la cuenta "+ idCuenta,1,"eliminarCuenta");
        } catch (CuentaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarCuenta(String idCuentaActual, CuentaDto cuentaDto) {
        try {
            Cuenta cuenta = mapper.cuentaDtoToCuenta(cuentaDto);
            getBilleteraVirtualUq().actualizarCuenta(idCuentaActual, cuenta);
            registrarAccionesSistema("Se actualizo la cuenta "+ cuenta.getIdCuenta(),1,"actualizarCuenta");
            return true;
        } catch (CuentaException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void cargarResourceXML() {
        billeteraVirtualUq = Persistencia.cargarRecursosBilleteraVirtualUqXML();
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoBilleteraVirtualUqXML(billeteraVirtualUq);
    }

    private void cargarResourceBinario() {
        billeteraVirtualUq = Persistencia.cargarRecursoBilleteraVirtualUqBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursosBilleteraVirtualUqBinario(billeteraVirtualUq);
    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
}
