package com.luisdeveloper.billeteravirtualuq.model;

import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.model.services.IBilleteraVirtualUqServices;

import java.io.Serializable;
import java.util.ArrayList;

public class BilleteraVirtualUq implements IBilleteraVirtualUqServices, Serializable {

    private static final long serialVersionUID = 1L;
    ArrayList<Cuenta> listaCuentas = new ArrayList<>();

    public BilleteraVirtualUq() {

    }


    public ArrayList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }




    @Override
    public Cuenta crearCuenta(String idCuenta, String nombreBanco,String numeroCuenta, String tipoCuenta ) throws CuentaException {
        Cuenta nuevoCuenta = null;
        boolean cuentaExiste = verificarCuentaExistente(idCuenta);
        if(cuentaExiste){
            throw new CuentaException("El cuenta con id: "+idCuenta+" ya existe");
        }else{
            nuevoCuenta = new Cuenta();
            nuevoCuenta.setIdCuenta(idCuenta);
            nuevoCuenta.setNombreBanco(nombreBanco);
            nuevoCuenta.setNumeroCuenta(numeroCuenta);
            nuevoCuenta.setTipoCuenta(tipoCuenta);

            getListaCuentas().add(nuevoCuenta);
        }
        return nuevoCuenta;
    }

    public void agregarEmpleado(Cuenta nuevoEmpleado) throws CuentaException{
        getListaCuentas().add(nuevoEmpleado);
    }

    @Override
    public boolean actualizarCuenta(String idActual, Cuenta cuenta) throws CuentaException {
        Cuenta cuentaActual = obtenerCuenta(idActual);
        if(cuentaActual == null)
            throw new CuentaException("El cuenta a actualizar no existe");
        else{
            cuentaActual.setIdCuenta(cuenta.getIdCuenta());
            cuentaActual.setNombreBanco(cuenta.getNombreBanco());
            cuentaActual.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaActual.setTipoCuenta(cuenta.getTipoCuenta());

            return true;
        }
    }

    @Override
    public Boolean eliminarCuenta(String idCuenta) throws CuentaException {
        Cuenta cuenta = null;
        boolean flagExiste = false;
        cuenta = obtenerCuenta(idCuenta);
        if(cuenta == null)
            throw new CuentaException("La cuenta a eliminar no existe");
        else{
            getListaCuentas().remove(cuenta);
            flagExiste = true;
        }
        return flagExiste;
    }

    @Override
    public boolean verificarCuentaExistente(String idCuenta) throws CuentaException {
        if(cuentaExiste(idCuenta)){
            throw new CuentaException("La cuenta con id: "+idCuenta+" ya existe");
        }else{
            return false;
        }
    }


    @Override
    public Cuenta obtenerCuenta(String idCuenta) {
        Cuenta cuentaEncontrado = null;
        for (Cuenta cuenta : getListaCuentas()) {
            if(cuenta.getIdCuenta().equalsIgnoreCase(idCuenta)){
                cuentaEncontrado = cuenta;
                break;
            }
        }
        return cuentaEncontrado;
    }

    @Override
    public ArrayList<Cuenta> obtenerCuentas() {
        // TODO Auto-generated method stub
        return getListaCuentas();
    }

    public boolean cuentaExiste(String idCuenta) {
        boolean cuentaEncontrado = false;
        for (Cuenta cuenta : getListaCuentas()) {
            if(cuenta.getIdCuenta().equalsIgnoreCase(idCuenta)){
                cuentaEncontrado = true;
                break;
            }
        }
        return cuentaEncontrado;
    }
}
