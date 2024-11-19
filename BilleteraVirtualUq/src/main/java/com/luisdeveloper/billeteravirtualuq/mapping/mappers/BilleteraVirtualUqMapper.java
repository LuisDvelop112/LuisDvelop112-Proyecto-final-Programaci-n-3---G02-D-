package com.luisdeveloper.billeteravirtualuq.mapping.mappers;

import com.luisdeveloper.billeteravirtualuq.mapping.dto.*;
import com.luisdeveloper.billeteravirtualuq.model.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BilleteraVirtualUqMapper {

    BilleteraVirtualUqMapper INSTANCE = Mappers.getMapper(BilleteraVirtualUqMapper.class);

    // Mapeo para Cuenta
    @Named("cuentaToCuentaDto")
    CuentaDto cuentaToCuentaDto(Cuenta cuenta);

    Cuenta cuentaDtoToCuenta(CuentaDto cuentaDto);

    @IterableMapping(qualifiedByName = "cuentaToCuentaDto")
    List<CuentaDto> getCuentasDto(List<Cuenta> listaCuentas);

    // Mapeo para Usuario
    @Named("usuarioToUsuarioDto")
    @Mapping(target = "cuentasBancarias", ignore = true) // Ignorar propiedades no mapeables
    UsuarioDto usuarioToUsuarioDto(Usuario usuario);

    Usuario usuarioDtoToUsuario(UsuarioDto usuarioDto);

    @IterableMapping(qualifiedByName = "usuarioToUsuarioDto")
    List<UsuarioDto> getUsuariosDto(List<Usuario> listaUsuarios);

    // Mapeo para Transacción
    @Named("transaccionToTransaccionDto")
    TransaccionDto transaccionToTransaccionDto(Transaccion transaccion);

    Transaccion transaccionDtoToTransaccion(TransaccionDto transaccionDto);

    @IterableMapping(qualifiedByName = "transaccionToTransaccionDto")
    List<TransaccionDto> getTransaccionesDto(List<Transaccion> listaTransacciones);

    // Mapeo para Presupuesto
    @Named("presupuestoToPresupuestoDto")
    PresupuestoDto presupuestoToPresupuestoDto(Presupuesto presupuesto);

    Presupuesto presupuestoDtoToPresupuesto(PresupuestoDto presupuestoDto);

    @IterableMapping(qualifiedByName = "presupuestoToPresupuestoDto")
    List<PresupuestoDto> getPresupuestosDto(List<Presupuesto> listaPresupuestos);

    // Mapeo para Categoría
    @Named("categoriaToCategoriaDto")
    CategoriaDto categoriaToCategoriaDto(Categoria categoria);

    Categoria categoriaDtoToCategoria(CategoriaDto categoriaDto);

    @IterableMapping(qualifiedByName = "categoriaToCategoriaDto")
    List<CategoriaDto> getCategoriasDto(List<Categoria> listaCategorias);

}
