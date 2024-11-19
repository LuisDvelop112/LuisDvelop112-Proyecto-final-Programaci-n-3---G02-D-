package com.luisdeveloper.billeteravirtualuq.utils;

import com.luisdeveloper.billeteravirtualuq.model.BilleteraVirtualUq;
import com.luisdeveloper.billeteravirtualuq.model.Categoria;
import com.luisdeveloper.billeteravirtualuq.model.Cuenta;
import com.luisdeveloper.billeteravirtualuq.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BilleteraVirtualUqUtils {

    public static BilleteraVirtualUq inicializarDatos() {
        BilleteraVirtualUq billeteraVirtualUq = new BilleteraVirtualUq();

        // Crear lista de cuentas bancarias para un usuario común
        List<Cuenta> cuentasBancariasUsuario = new ArrayList<>();
        cuentasBancariasUsuario.add(new Cuenta("123456", "Bank", "123", "Ahorros"));
        cuentasBancariasUsuario.add(new Cuenta("654321", "BankPlus", "456", "Corriente"));

        // Crear un Usuario común con los datos iniciales
        Usuario usuarioComún = new Usuario(
                "1", // ID del usuario
                "Luis Developer", // Nombre completo
                "luis@example.com", // Correo electrónico
                "3001234567", // Número de teléfono
                "Calle 123, Ciudad X", // Dirección
                1000.0, // Saldo total
                "1", // Contraseña
                cuentasBancariasUsuario, // Lista de cuentas bancarias
                new ArrayList<>(), // Lista vacía de transacciones
                new ArrayList<>() // Lista vacía de presupuestos
        );

        // Agregar el usuario común a la billetera virtual
        billeteraVirtualUq.agregarUsuario(usuarioComún);

        // Crear lista de cuentas bancarias para el admin
        List<Cuenta> cuentasBancariasAdmin = new ArrayList<>();
        cuentasBancariasAdmin.add(new Cuenta("987654", "BankAdmin", "789", "Ahorros"));
        cuentasBancariasAdmin.add(new Cuenta("456789", "BankPlusAdmin", "012", "Corriente"));

        // Crear un Usuario Admin con los datos iniciales
        Usuario admin = new Usuario(
                "Admin", // ID del admin
                "Admin Usuario", // Nombre completo
                "admin@example.com", // Correo electrónico
                "3009876543", // Número de teléfono
                "Calle Admin, Ciudad X", // Dirección
                5000.0, // Saldo total (más alto para el admin)
                "2", // Contraseña
                cuentasBancariasAdmin, // Lista de cuentas bancarias del admin
                new ArrayList<>(), // Lista vacía de transacciones
                new ArrayList<>() // Lista vacía de presupuestos
        );
        
        // Asumir que el campo "rol" o algún indicador en la clase Usuario permite identificar si es admin // Establecer el rol de usuario como "ADMIN" para el administrador

        // Agregar el administrador a la billetera virtual
        billeteraVirtualUq.agregarUsuario(admin);

        // Crear categorías de transacciones
        billeteraVirtualUq.crearCategoria(new Categoria("1", "Alimentación", "Gastos relacionados con comida y bebidas"));
        billeteraVirtualUq.crearCategoria(new Categoria("2", "Transporte", "Gastos relacionados con transporte público o privado"));
        billeteraVirtualUq.crearCategoria(new Categoria("3", "Entretenimiento", "Gastos relacionados con actividades recreativas"));

        System.out.println("Usuarios inicializados correctamente: ");
        System.out.println("Usuario común: " + usuarioComún);
        System.out.println("Administrador: " + admin);

        return billeteraVirtualUq;
    }
}
