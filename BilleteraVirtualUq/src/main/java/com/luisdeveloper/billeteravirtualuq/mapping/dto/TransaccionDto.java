package com.luisdeveloper.billeteravirtualuq.mapping.dto;

import java.time.LocalDateTime;

public record TransaccionDto(
        String idTransaccion,
        LocalDateTime fecha,
        String tipoTransaccion,
        double monto,
        String descripcion,
        String cuentaOrigen,
        String cuentaDestino,
        CategoriaDto categoria) {

}
