package com.luisdeveloper.billeteravirtualuq.controller.services;

import java.time.LocalDateTime;
import java.util.List;

import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import com.luisdeveloper.billeteravirtualuq.model.Categoria;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;
import com.luisdeveloper.billeteravirtualuq.model.Presupuesto;
import com.luisdeveloper.billeteravirtualuq.model.Transaccion;

public interface IModelFactoryService {


    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String idUsuario) throws UsuarioNoEncontradoException;

    boolean actualizarUsuario(String idUsuarioActual, UsuarioDto usuarioDto) throws UsuarioNoEncontradoException;

    UsuarioDto obtenerUsuario(String idUsuario);

    boolean iniciarSesion(String correoElectronico, String contrasena);

    double obtenerSaldo(String idUsuario);

    public List<UsuarioDto> obtenerTodosUsuarios();

    public boolean aumentarSaldo(String idUsuario, double nuevoSaldo);

    public boolean reducirSaldo(String idUsuario, double nuevoSaldo);

    public boolean agregarTransaccion(String idUsuario, TransaccionDto nuevaTransaccion);

    public boolean eliminarTransaccion(String idUsuario, String idTransaccion);

    public boolean actualizarTransaccion(String idUsuario, String idTransaccion, Transaccion transaccionActualizada);

    public Transaccion obtenerTransaccion(String idUsuario, String idTransaccion);

    public List<Transaccion> listarTransacciones(String idUsuario);

    public List<Transaccion> filtrarTransaccionesPorFecha(String idUsuario, LocalDateTime fechaInicio,
            LocalDateTime fechaFin);

    public List<Transaccion> filtrarTransaccionesPorTipo(String idUsuario, String tipoTransaccion);

    public List<Transaccion> filtrarTransaccionesPorCategoria(String idUsuario, String categoriaId);

        public boolean agregarPresupuesto(String idUsuario, Presupuesto nuevoPresupuesto);

    public boolean actualizarPresupuesto(String idUsuario, String idPresupuesto, Presupuesto presupuestoActualizado);

    public boolean eliminarPresupuesto(String idUsuario, String idPresupuesto);

    public Presupuesto buscarPresupuesto(String idUsuario, String idPresupuesto);

    public String consultarEstadoPresupuesto(String idUsuario, String idPresupuesto);

    public double calcularGastoPorCategoria(String idUsuario, String idCategoria);

        public boolean agregarCuenta(String idUsuario);

    public boolean agregarCuentasUsuario(String idUsuario, CuentaDto cuentasDto);
    
    public CuentaDto leerCuenta(String idUsuario, String idCuenta);

    public boolean actualizarCuenta(String idUsuario, String idCuenta, CuentaDto cuentaDtoActualizada) throws UsuarioNoEncontradoException, CuentaException;

    public void eliminarCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException, CuentaException;

    public Cuenta obtenerCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException;

        public boolean crearCategoria(CategoriaDto categoriaDTO);

    public void asignarCategoriaATransaccion(Categoria categoria, String idCategoria, String IdUsuario, String IdTransaccion);
    
    public void actualizarCategoria(String idCategoria, String nuevoNombre, String nuevaDescripcion);

    public void eliminarCategoria(String idCategoria);

    public void listarCategorias();

    public List<Cuenta> obtenerCuentas(String idUsuario);

    public Categoria buscarCategoria(String idCategoria);

    public List<Presupuesto> devolverPresupuestos(String idUsuario);

    public void guardarDatos();
}
