package com.luisdeveloper.billeteravirtualuq.model;

import java.io.Serializable;
import java.util.Objects;

public class Presupuesto implements Serializable{
    private String idPresupuesto;
    private String nombre;
    private double montoTotal;
    private double montoGastado;
    private Categoria categoria;

    public Presupuesto(String idPresupuesto, String nombre, double montoTotal, double montoGastado,
            Categoria categoria) {
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.montoGastado = montoGastado;
        this.categoria = categoria;
    }

    public String getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(String idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getMontoGastado() {
        return montoGastado;
    }

    public void setMontoGastado(double montoGastado) {
        this.montoGastado = montoGastado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presupuesto that = (Presupuesto) o;
        return Double.compare(that.montoTotal, montoTotal) == 0 &&
            Double.compare(that.montoGastado, montoGastado) == 0 &&
            Objects.equals(idPresupuesto, that.idPresupuesto) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(categoria, that.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPresupuesto, nombre, montoTotal, montoGastado, categoria);
    }

    @Override
    public String toString() {
        return "Presupuesto{" +
            "idPresupuesto='" + idPresupuesto + '\'' +
            ", nombre='" + nombre + '\'' +
            ", montoTotal=" + montoTotal +
            ", montoGastado=" + montoGastado +
            ", categoria=" + categoria +
            '}';
    }
}
