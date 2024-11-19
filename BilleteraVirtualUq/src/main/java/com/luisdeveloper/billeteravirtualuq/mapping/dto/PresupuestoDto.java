package com.luisdeveloper.billeteravirtualuq.mapping.dto;

public record PresupuestoDto(
        String idPresupuesto,
        String nombre,
        double montoTotal,
        double montoGastado,
        CategoriaDto categoria) {

}
