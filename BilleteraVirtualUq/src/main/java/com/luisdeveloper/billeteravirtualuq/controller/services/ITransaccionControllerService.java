package com.luisdeveloper.billeteravirtualuq.controller.services;

import com.luisdeveloper.billeteravirtualuq.model.Transaccion;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransaccionControllerService {

    public boolean agregarTransaccion(String idUsuario, Transaccion nuevaTransaccion);

    public boolean eliminarTransaccion(String idUsuario, String idTransaccion);

    public boolean actualizarTransaccion(String idUsuario, String idTransaccion, Transaccion transaccionActualizada);

    public Transaccion obtenerTransaccion(String idUsuario, String idTransaccion);

    public List<Transaccion> listarTransacciones(String idUsuario);

    public List<Transaccion> filtrarTransaccionesPorFecha(String idUsuario, LocalDateTime fechaInicio,
            LocalDateTime fechaFin);

    public List<Transaccion> filtrarTransaccionesPorTipo(String idUsuario, String tipoTransaccion);

    public List<Transaccion> filtrarTransaccionesPorCategoria(String idUsuario, String categoriaId);
}
