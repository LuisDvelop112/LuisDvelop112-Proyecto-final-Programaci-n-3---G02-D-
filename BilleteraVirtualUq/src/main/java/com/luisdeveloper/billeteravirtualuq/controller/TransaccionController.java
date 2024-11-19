package com.luisdeveloper.billeteravirtualuq.controller;


import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;

import java.time.LocalDateTime;
import java.util.List;

public class TransaccionController  {

    ModelFactoryController modelFactoryController;

    public TransaccionController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean agregarTransaccion(String idUsuario, TransaccionDto nuevaTransaccion) {
        return modelFactoryController.agregarTransaccion(idUsuario, nuevaTransaccion);
    }

    public boolean eliminarTransaccion(String idUsuario, String idTransaccion) {
        return modelFactoryController.eliminarTransaccion(idUsuario, idTransaccion);
    }

    public boolean actualizarTransaccion(String idUsuario, String idTransaccion, Transaccion transaccionActualizada) {
        return modelFactoryController.actualizarTransaccion(idUsuario, idTransaccion, transaccionActualizada);
    }

    public Transaccion obtenerTransaccion(String idUsuario, String idTransaccion) {
        return modelFactoryController.obtenerTransaccion(idUsuario, idTransaccion);
    }

    public List<Transaccion> listarTransacciones(String idUsuario) {
        return modelFactoryController.listarTransacciones(idUsuario);
    }

    public List<Transaccion> filtrarTransaccionesPorFecha(String idUsuario, LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        return modelFactoryController.filtrarTransaccionesPorFecha(idUsuario, fechaInicio, fechaFin);
    }

    public List<Transaccion> filtrarTransaccionesPorTipo(String idUsuario, String tipoTransaccion) {
        return modelFactoryController.filtrarTransaccionesPorTipo(idUsuario, tipoTransaccion);
    }

    public List<Transaccion> filtrarTransaccionesPorCategoria(String idUsuario, String categoriaId) {
        return modelFactoryController.filtrarTransaccionesPorCategoria(idUsuario, categoriaId);
    }
}
