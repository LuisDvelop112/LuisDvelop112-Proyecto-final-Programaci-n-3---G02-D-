package com.luisdeveloper.billeteravirtualuq.controller.services;

import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;

import java.util.List;

public interface IModelFactoryService {

    List<CuentaDto> obtenerCuentas();

    boolean agregarCuenta(CuentaDto cuentaDto);

    boolean eliminarCuenta(String idCuenta);

    boolean actualizarCuenta(String idCuentaActual, CuentaDto cuentaDto);
}
