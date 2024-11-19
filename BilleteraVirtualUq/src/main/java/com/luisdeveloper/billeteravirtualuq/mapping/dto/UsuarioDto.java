package com.luisdeveloper.billeteravirtualuq.mapping.dto;

import java.util.List;

public record UsuarioDto(
        String idUsuario,
        String nombreCompleto,
        String correoElectronico,
        String numeroTelefono,
        String direccion,
        double saldoTotal,
        String contrasena,
        List<CuentaDto> cuentasBancarias,
        List<TransaccionDto> transacciones,
        List<PresupuestoDto> presupuestos) {
}
