package com.luisdeveloper.billeteravirtualuq.controller.services;

import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;

import java.util.List;

public interface ICuentaControllerService {
    List<CuentaDto> obtenerCuentas();

    boolean agregarCuenta(CuentaDto cuentaDtoDto);

    boolean eliminarCuenta(String idCuenta);

    boolean actualizarCuenta(String idCuentaActual, CuentaDto cuentaDto);
}
