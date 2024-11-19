package com.luisdeveloper.billeteravirtualuq.controller.services;

import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import java.util.List;

public interface ICuentaControllerService {

    public boolean agregarCuenta(String idUsuario);

    public boolean agregarCuentasUsuario(String idUsuario, CuentaDto cuentasDto);
    
    public CuentaDto leerCuenta(String idUsuario, String idCuenta);

    public boolean actualizarCuenta(String idUsuario, String idCuenta, CuentaDto cuentaDtoActualizada) throws UsuarioNoEncontradoException, CuentaException;

    public void eliminarCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException, CuentaException;

    public Cuenta obtenerCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException;

    public List<Cuenta> obtenerCuentas(String idUsuario);
}
