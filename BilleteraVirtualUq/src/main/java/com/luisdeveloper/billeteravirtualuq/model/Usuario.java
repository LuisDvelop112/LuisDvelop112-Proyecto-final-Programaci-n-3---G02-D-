package com.luisdeveloper.billeteravirtualuq.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable{
    private String idUsuario;
    private String nombreCompleto;
    private String correoElectronico;
    private String numeroTelefono;
    private String direccion;
    private double saldoTotal;
    private String contrasena;
    private List<Cuenta> cuentasBancarias;
    private List<Transaccion> transacciones;
    private List<Presupuesto> presupuestos;

    public Usuario(String idUsuario, String nombreCompleto, String correoElectronico, String numeroTelefono,
            String direccion, double saldoTotal, String contrasena, List<Cuenta> cuentasBancarias,
            List<Transaccion> transacciones, List<Presupuesto> presupuestos) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.direccion = direccion;
        this.saldoTotal = saldoTotal;
        this.contrasena = contrasena;
        this.cuentasBancarias = cuentasBancarias;
        this.transacciones = transacciones;
        this.presupuestos = presupuestos;
    }
    
    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }
    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public double getSaldoTotal() {
        return saldoTotal;
    }
    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public List<Cuenta> getCuentasBancarias() {
        return cuentasBancarias;
    }
    public void setCuentasBancarias(List<Cuenta> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }
    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombreCompleto=" + nombreCompleto + ", correoElectronico="
                + correoElectronico + ", numeroTelefono=" + numeroTelefono + ", direccion=" + direccion
                + ", saldoTotal=" + saldoTotal + ", contrasena=" + contrasena + ", cuentasBancarias=" + cuentasBancarias
                + "]";
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    
}
