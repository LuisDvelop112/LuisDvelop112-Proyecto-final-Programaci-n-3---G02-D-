package com.luisdeveloper.billeteravirtualuq.model.services;

import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import java.util.ArrayList;

public interface IBilleteraVirtualUqServices {

    public Cuenta crearCuenta(String idCuenta, String nombreBanco, String numeroCuenta, String tipoCuenta) throws CuentaException;

    public Boolean eliminarCuenta(String idCuenta)throws CuentaException;

    boolean actualizarCuenta(String idActual, Cuenta cuenta) throws CuentaException;

    public boolean  verificarCuentaExistente(String idActual) throws CuentaException;

    public Cuenta obtenerCuenta(String idActual);

    public ArrayList<Cuenta> obtenerCuentas();
}
