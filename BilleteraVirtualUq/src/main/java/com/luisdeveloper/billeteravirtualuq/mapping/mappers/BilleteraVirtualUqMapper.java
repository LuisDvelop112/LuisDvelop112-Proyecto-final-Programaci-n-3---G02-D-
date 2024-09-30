package com.luisdeveloper.billeteravirtualuq.mapping.mappers;

import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BilleteraVirtualUqMapper {

    BilleteraVirtualUqMapper INSTANCE = Mappers.getMapper(BilleteraVirtualUqMapper.class);

    @Named("cuentaToCuentaDto")
   CuentaDto cuentaToCuentaDto(Cuenta cuenta);

    Cuenta cuentaDtoToCuenta(CuentaDto cuentaDto);

    @IterableMapping(qualifiedByName = "cuentaToCuentaDto")
    List<CuentaDto> getCuentasDto(List<Cuenta> listaCuentas);

}
