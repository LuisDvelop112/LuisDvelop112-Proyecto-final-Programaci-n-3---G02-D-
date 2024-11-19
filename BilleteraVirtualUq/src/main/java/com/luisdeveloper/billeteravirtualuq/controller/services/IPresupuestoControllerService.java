package com.luisdeveloper.billeteravirtualuq.controller.services;

import java.util.List;

import com.luisdeveloper.billeteravirtualuq.model.Presupuesto;

public interface IPresupuestoControllerService {

    public boolean agregarPresupuesto(String idUsuario, Presupuesto nuevoPresupuesto);

    public boolean actualizarPresupuesto(String idUsuario, String idPresupuesto, Presupuesto presupuestoActualizado);

    public boolean eliminarPresupuesto(String idUsuario, String idPresupuesto);

    public Presupuesto buscarPresupuesto(String idUsuario, String idPresupuesto);

    public String consultarEstadoPresupuesto(String idUsuario, String idPresupuesto);

    public double calcularGastoPorCategoria(String idUsuario, String idCategoria);
    
    public List<Presupuesto> devolverPresupuestos(String idUsuario);
}
