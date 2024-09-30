package com.luisdeveloper.billeteravirtualuq.controller;

import com.luisdeveloper.billeteravirtualuq.controller.services.ICuentaControllerService;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;

import java.util.List;

public class CuentaController implements ICuentaControllerService{

    ModelFactoryController modelFactoryController;

    public CuentaController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public List<CuentaDto> obtenerCuentas() {
        return modelFactoryController.obtenerCuentas();
    }

    @Override
    public boolean agregarCuenta(CuentaDto cuentaDto) {
        return modelFactoryController.agregarCuenta(cuentaDto);
    }

    @Override
    public boolean eliminarCuenta(String idCuenta) {
        return modelFactoryController.eliminarCuenta(idCuenta);
    }

    @Override
    public boolean actualizarCuenta(String idCuentaActual, CuentaDto cuentaDto) {
        return modelFactoryController.actualizarCuenta(idCuentaActual,cuentaDto);
    }
}
