package com.luisdeveloper.billeteravirtualuq.controller;

import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;
import com.luisdeveloper.billeteravirtualuq.model.Categoria;

public class CategoriaController {
    
    ModelFactoryController modelFactoryController;

    public CategoriaController(){
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public boolean crearCategoria(CategoriaDto categoriaDTO){
        return modelFactoryController.crearCategoria(categoriaDTO);
    }

    public void asignarCategoriaATransaccion(Categoria categoria, String idCategoria, String IdUsuario, String IdTransaccion){
        modelFactoryController.asignarCategoriaATransaccion(categoria, idCategoria, IdUsuario, IdTransaccion);
    }
    
    public void actualizarCategoria(String idCategoria, String nuevoNombre, String nuevaDescripcion){
        modelFactoryController.actualizarCategoria(idCategoria, nuevoNombre, nuevaDescripcion);
    }

    public void eliminarCategoria(String idCategoria){
        modelFactoryController.eliminarCategoria(idCategoria);
    }

    public void listarCategorias(){
        modelFactoryController.listarCategorias();
    }

    public Categoria buscarCategoria(String idCategoria){
        return modelFactoryController.buscarCategoria(idCategoria);
    }
}
