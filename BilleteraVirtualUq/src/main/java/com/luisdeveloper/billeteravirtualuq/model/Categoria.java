package com.luisdeveloper.billeteravirtualuq.model;

import java.io.Serializable;
import java.util.Objects;

public class Categoria implements Serializable{

    private String idCategoria;
    private String nombre;
    private String descripcion;

    public Categoria(String idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    } 

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(idCategoria, categoria.idCategoria) &&
            Objects.equals(nombre, categoria.nombre) &&
            Objects.equals(descripcion, categoria.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria, nombre, descripcion);
    }

    @Override
    public String toString() {
        return "Categoria{" +
            "idCategoria='" + idCategoria + '\'' +
            ", nombre='" + nombre + '\'' +
            ", descripcion='" + descripcion + '\'' +
            '}';
    }
}
