package com.luisdeveloper.billeteravirtualuq.model;

import java.io.Serializable;
import java.util.Objects;

public class Cuenta implements Serializable{

    private String idCuenta;
    private String nombreBanco;
    private String numeroCuenta;
    private String tipoCuenta;

    public Cuenta(String idCuenta, String nombreBanco, String numeroCuenta, String tipoCuenta) {
        this.idCuenta = idCuenta;
        this.nombreBanco = nombreBanco;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(idCuenta, cuenta.idCuenta) && Objects.equals(nombreBanco, cuenta.nombreBanco) && Objects.equals(numeroCuenta, cuenta.numeroCuenta) && Objects.equals(tipoCuenta, cuenta.tipoCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCuenta, nombreBanco, numeroCuenta, tipoCuenta);
    }

    @Override
    public String toString() {
        return "Cuenta [idCuenta=" + idCuenta + ", nombreBanco=" + nombreBanco + ", numeroCuenta=" + numeroCuenta
                + ", tipoCuenta=" + tipoCuenta;
    }

}
