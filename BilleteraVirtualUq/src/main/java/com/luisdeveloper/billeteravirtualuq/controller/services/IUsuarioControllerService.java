package com.luisdeveloper.billeteravirtualuq.controller.services;

import java.util.List;

import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import com.luisdeveloper.billeteravirtualuq.model.Usuario;

public interface IUsuarioControllerService {
    
    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String idUsuario) throws UsuarioNoEncontradoException;

    boolean actualizarUsuario(String idUsuarioActual, UsuarioDto usuarioDto) throws UsuarioNoEncontradoException;

    UsuarioDto obtenerUsuario(String idUsuario);

    boolean iniciarSesion(String correoElectronico, String contrasena);

    double obtenerSaldo(String idUsuario);

    public List<Usuario> obtenerTodosUsuarios();

    public boolean actualizarSaldo(String idUsuario, double nuevoSaldo);

    public boolean aumentarSaldo(String idUsuario, double nuevoSaldo);

    public boolean reducirSaldo(String idUsuario, double nuevoSaldo);

    public void guardarDatos();
}
