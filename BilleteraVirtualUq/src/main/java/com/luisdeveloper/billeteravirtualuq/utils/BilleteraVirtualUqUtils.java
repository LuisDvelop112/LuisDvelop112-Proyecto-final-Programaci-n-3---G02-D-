package com.luisdeveloper.billeteravirtualuq.utils;

import com.luisdeveloper.billeteravirtualuq.model.Cuenta;
import com.luisdeveloper.billeteravirtualuq.model.BilleteraVirtualUq;

public class BilleteraVirtualUqUtils {

    public static BilleteraVirtualUq inicializarDatos() {
        BilleteraVirtualUq billeteraVirtualUq = new BilleteraVirtualUq();

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta("123456");
        cuenta.setNombreBanco("Bank");
        cuenta.setNumeroCuenta("123");
        cuenta.setTipoCuenta("Ahorros");

        billeteraVirtualUq.getListaCuentas().add(cuenta);

        cuenta = new Cuenta();
        cuenta.setIdCuenta("46236326");
        cuenta.setNombreBanco("BankO");
        cuenta.setNumeroCuenta("456");
        cuenta.setTipoCuenta("Corriente");


        billeteraVirtualUq.getListaCuentas().add(cuenta);

        cuenta = new Cuenta();
        cuenta.setIdCuenta("11232444");
        cuenta.setNombreBanco("GrupoAval");
        cuenta.setNumeroCuenta("789");
        cuenta.setTipoCuenta("Ahorros");

        billeteraVirtualUq.getListaCuentas().add(cuenta);
        System.out.println("Información de la cuenta creada");
        return billeteraVirtualUq;
    }
}
