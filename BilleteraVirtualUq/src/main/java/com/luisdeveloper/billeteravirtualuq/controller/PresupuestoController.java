package com.luisdeveloper.billeteravirtualuq.controller;

import java.util.List;

import com.luisdeveloper.billeteravirtualuq.model.Presupuesto;

public class PresupuestoController {

    ModelFactoryController modelFactoryController;

    public PresupuestoController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean agregarPresupuesto(String idUsuario, Presupuesto nuevoPresupuesto) {
        return modelFactoryController.agregarPresupuesto(idUsuario, nuevoPresupuesto);
    }

    public boolean actualizarPresupuesto(String idUsuario, String idPresupuesto, Presupuesto presupuestoActualizado) {
        return modelFactoryController.actualizarPresupuesto(idUsuario, idPresupuesto, presupuestoActualizado);
    }

    public boolean eliminarPresupuesto(String idUsuario, String idPresupuesto) {
        return modelFactoryController.eliminarPresupuesto(idUsuario, idPresupuesto);
    }

    public Presupuesto buscarPresupuesto(String idUsuario, String idPresupuesto) {
        return modelFactoryController.buscarPresupuesto(idUsuario, idPresupuesto);
    }

    public String consultarEstadoPresupuesto(String idUsuario, String idPresupuesto) {
        return modelFactoryController.consultarEstadoPresupuesto(idUsuario, idPresupuesto);
    }

    public double calcularGastoPorCategoria(String idUsuario, String idCategoria) {
        return modelFactoryController.calcularGastoPorCategoria(idUsuario, idCategoria);
    }

    public List<Presupuesto> devolverPresupuestos(String idUsuario){
        return modelFactoryController.devolverPresupuestos(idUsuario);
    }
}
