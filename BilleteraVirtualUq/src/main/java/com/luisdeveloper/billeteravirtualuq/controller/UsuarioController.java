package com.luisdeveloper.billeteravirtualuq.controller;

import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;

import java.util.List;

public class UsuarioController {

    private ModelFactoryController modelFactoryController;

    public UsuarioController() {
        this.modelFactoryController = ModelFactoryController.getInstance(); // Uso de modelFactoryService consistente
    }

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        return modelFactoryController.agregarUsuario(usuarioDto);
    }

    public boolean eliminarUsuario(String idUsuario) throws UsuarioNoEncontradoException {
        return modelFactoryController.eliminarUsuario(idUsuario);
    }

    public boolean actualizarUsuario(String idUsuarioActual, UsuarioDto usuarioDtoActualizado) throws UsuarioNoEncontradoException {
        return modelFactoryController.actualizarUsuario(idUsuarioActual, usuarioDtoActualizado);
    }

    public UsuarioDto obtenerUsuario(String idUsuario) {
        return modelFactoryController.obtenerUsuario(idUsuario);
    }

    public List<UsuarioDto> obtenerTodosUsuarios() {
        return modelFactoryController.obtenerTodosUsuarios();
    }

    public boolean iniciarSesion(String IdUsuario, String contrasena) {
        return modelFactoryController.iniciarSesion(IdUsuario, contrasena);
    }

    public double obtenerSaldo(String idUsuario) {
        return modelFactoryController.obtenerSaldo(idUsuario);
    }

    public boolean aumentarSaldo(String idUsuario, double nuevoSaldo) {
        return modelFactoryController.aumentarSaldo(idUsuario, nuevoSaldo);
    }

    public boolean reducirSaldo(String idUsuario, double nuevoSaldo) {
        return modelFactoryController.reducirSaldo(idUsuario, nuevoSaldo);
    }

    public void guardarDatos(){
        modelFactoryController.guardarDatos();
    }
    
}
