package com.luisdeveloper.billeteravirtualuq.controller;


import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import java.util.List;

public class CuentaController{

    ModelFactoryController modelFactoryController;

    public CuentaController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }


    public boolean agregarCuenta(String idUsuario) {
        return modelFactoryController.agregarCuenta(idUsuario);
    }


    public boolean agregarCuentasUsuario(String idUsuario, CuentaDto cuentasDto) {
        return modelFactoryController.agregarCuentasUsuario(idUsuario, cuentasDto);
    }


    public CuentaDto leerCuenta(String idUsuario, String idCuenta) {
        return modelFactoryController.leerCuenta(idUsuario, idCuenta);
    }

    public boolean actualizarCuenta(String idUsuario, String idCuenta, CuentaDto cuentaDtoActualizada) throws UsuarioNoEncontradoException, CuentaException {
        return modelFactoryController.actualizarCuenta(idUsuario, idCuenta, cuentaDtoActualizada);
    }


    public void eliminarCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException, CuentaException {
        modelFactoryController.eliminarCuenta(idUsuario, idCuenta);
    }


    public Cuenta obtenerCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException {
        return modelFactoryController.obtenerCuenta(idUsuario, idCuenta);
    }

    public List<Cuenta> obtenerCuentas(String idUsuario){
        return modelFactoryController.obtenerCuentas(idUsuario);
    }

}
