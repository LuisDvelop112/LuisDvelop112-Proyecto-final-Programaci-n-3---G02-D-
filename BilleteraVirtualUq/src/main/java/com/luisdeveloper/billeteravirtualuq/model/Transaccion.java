package com.luisdeveloper.billeteravirtualuq.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaccion implements Serializable{

    private String idTransaccion;
    private LocalDateTime fecha;
    private String tipoTransaccion;
    private double monto;
    private String descripcion;
    private String cuentaOrigen; 
    private String cuentaDestino; 
    private Categoria categoria;

    public Transaccion(String idTransaccion, LocalDateTime fecha, String tipoTransaccion, double monto, String descripcion, String cuentaOrigen, String cuentaDestino, Categoria categoria) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.categoria = categoria;
    }

    public Transaccion() {
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Double.compare(that.monto, monto) == 0 &&
            Objects.equals(idTransaccion, that.idTransaccion) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(tipoTransaccion, that.tipoTransaccion) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(cuentaOrigen, that.cuentaOrigen) &&
            Objects.equals(cuentaDestino, that.cuentaDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaccion, fecha, tipoTransaccion, monto, descripcion, cuentaOrigen, cuentaDestino);
    }

    @Override
    public String toString() {
        return "Transaccion{" +
            "idTransaccion='" + idTransaccion + '\'' +
            ", fecha=" + fecha +
            ", tipoTransaccion='" + tipoTransaccion + '\'' +
            ", monto=" + monto +
            ", descripcion='" + descripcion + '\'' +
            ", cuentaOrigen=" + cuentaOrigen +
            ", cuentaDestino=" + cuentaDestino +
            '}';
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
