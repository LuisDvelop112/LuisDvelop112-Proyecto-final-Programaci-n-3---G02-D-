package com.luisdeveloper.billeteravirtualuq.model;

import com.luisdeveloper.billeteravirtualuq.exceptions.CuentaException;
import com.luisdeveloper.billeteravirtualuq.exceptions.UsuarioNoEncontradoException;
import com.luisdeveloper.billeteravirtualuq.model.services.IBilleteraVirtualUqServices;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BilleteraVirtualUq implements IBilleteraVirtualUqServices, Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    private ArrayList<Categoria> categorias = new ArrayList<>();
    

    // ============================
    // Métodos CRUD para Usuario
    // ============================

    public void agregarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (verificarUsuarioExistente(usuario.getIdUsuario())) {
            throw new IllegalArgumentException("El usuario con ID " + usuario.getIdUsuario() + " ya existe.");
        }
        listaUsuarios.add(usuario);
    }

    public Usuario leerUsuario(String idUsuario) throws UsuarioNoEncontradoException {
        return Optional.ofNullable(obtenerUsuario(idUsuario))
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
    }

    public boolean actualizarUsuario(String idUsuarioActual, Usuario usuarioActualizado)
            throws UsuarioNoEncontradoException {
        if (usuarioActualizado == null) {
            throw new IllegalArgumentException("El usuario actualizado no puede ser nulo.");
        }
        Usuario usuario = obtenerUsuario(idUsuarioActual);
        if (usuario != null) {
            int index = listaUsuarios.indexOf(usuario);
            listaUsuarios.set(index, usuarioActualizado);
            return true;
        } else {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + idUsuarioActual);
        }
    }

    public boolean eliminarUsuario(String idUsuario) throws UsuarioNoEncontradoException {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            listaUsuarios.remove(usuario);
            return true;
        } else {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + idUsuario);
        }
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }

    public boolean verificarUsuarioExistente(String idUsuario) {
        return listaUsuarios.stream().anyMatch(usuario -> usuario.getIdUsuario().equals(idUsuario));
    }

    public Usuario obtenerUsuario(String idUsuario) {
        return listaUsuarios.stream()
                .filter(u -> u.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    // ============================
    // Métodos Adicionales para Usuario
    // ============================

    public boolean aumentarSaldo(String idUsuario, double monto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && monto > 0) { // Validar que el usuario exista y el monto sea positivo
            usuario.setSaldoTotal(usuario.getSaldoTotal() + monto);
            return true;
        }
        return false;
    }

    public boolean reducirSaldo(String idUsuario, double monto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null && monto > 0 && usuario.getSaldoTotal() >= monto) { // Validar saldo suficiente
            usuario.setSaldoTotal(usuario.getSaldoTotal() - monto);
            return true;
        }
        return false;
    }
    

    // ============================
    // Métodos para Gestión de Cuentas
    // ============================

    public boolean agregarCuentasUsuario(String idUsuario, Cuenta cuentas) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            List<Cuenta> cuentasExistentes = usuario.getCuentasBancarias();
            cuentasExistentes.add(cuentas);
            usuario.setCuentasBancarias(cuentasExistentes);
            return true;
        }
        return false;
    }

    public Cuenta leerCuenta(String idUsuario, String idCuenta) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            return usuario.getCuentasBancarias().stream()
                    .filter(cuenta -> cuenta.getIdCuenta().equals(idCuenta))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean actualizarCuenta(String idUsuario, String idCuenta, Cuenta cuentaActualizada)
            throws UsuarioNoEncontradoException, CuentaException {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<Cuenta> cuentas = usuario.getCuentasBancarias();
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta cuenta = cuentas.get(i);
            if (cuenta.getIdCuenta().equals(idCuenta)) {
                cuentas.set(i, cuentaActualizada);
                return true;
            }
        }
        throw new CuentaException("Cuenta no encontrada con ID: " + idCuenta);
    }

    public void eliminarCuenta(String idUsuario, String idCuenta)
            throws UsuarioNoEncontradoException, CuentaException {

        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<Cuenta> cuentas = usuario.getCuentasBancarias();
        boolean eliminada = cuentas.removeIf(cuenta -> cuenta.getIdCuenta().equals(idCuenta));

        if (!eliminada) {
            throw new CuentaException("Cuenta no encontrada con ID: " + idCuenta);
        }
    }

    public Cuenta obtenerCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            if (usuario.getCuentasBancarias() != null) {
                return usuario.getCuentasBancarias().stream()
                        .filter(c -> c.getIdCuenta().equals(idCuenta))
                        .findFirst()
                        .orElse(null);
            }
        }
        throw new UsuarioNoEncontradoException("Usuario con ID " + idUsuario + " no encontrado.");
    }

    public List<Cuenta> obtenerCuentas(String idUsuario){
        Usuario usuario = obtenerUsuario(idUsuario);
        return usuario.getCuentasBancarias();
    }
    // =====================================
    // Métodos para Gestión de Transacciones
    // =====================================

    public boolean agregarTransaccion(String idUsuario, Transaccion nuevaTransaccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return false;
            System.out.println("R");
        return usuario.getTransacciones().add(nuevaTransaccion);
    }

    public boolean eliminarTransaccion(String idUsuario, String idTransaccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return false;

        return usuario.getTransacciones().removeIf(transaccion -> transaccion.getIdTransaccion().equals(idTransaccion));
    }

    public boolean actualizarTransaccion(String idUsuario, String idTransaccion, Transaccion transaccionActualizada) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return false;

        List<Transaccion> transacciones = usuario.getTransacciones();
        for (int i = 0; i < transacciones.size(); i++) {
            if (transacciones.get(i).getIdTransaccion().equals(idTransaccion)) {
                transacciones.set(i, transaccionActualizada);
                return true;
            }
        }
        return false;
    }

    public Transaccion obtenerTransaccion(String idUsuario, String idTransaccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return null;

        return usuario.getTransacciones()
                .stream()
                .filter(transaccion -> transaccion.getIdTransaccion().equals(idTransaccion))
                .findFirst()
                .orElse(null);
    }

    public List<Transaccion> listarTransacciones(String idUsuario) {
        Usuario usuario = obtenerUsuario(idUsuario);
        return (usuario != null) ? usuario.getTransacciones() : new ArrayList<>();
    }

    public List<Transaccion> filtrarTransaccionesPorFecha(String idUsuario, LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return new ArrayList<>();

        return usuario.getTransacciones()
                .stream()
                .filter(transaccion -> !transaccion.getFecha().isBefore(fechaInicio)
                        && !transaccion.getFecha().isAfter(fechaFin))
                .collect(Collectors.toList());
    }

    public List<Transaccion> filtrarTransaccionesPorTipo(String idUsuario, String tipoTransaccion) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return new ArrayList<>();

        return usuario.getTransacciones()
                .stream()
                .filter(transaccion -> transaccion.getTipoTransaccion().equalsIgnoreCase(tipoTransaccion))
                .collect(Collectors.toList());
    }

    public List<Transaccion> filtrarTransaccionesPorCategoria(String idUsuario, String categoriaId) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return new ArrayList<>();

        return usuario.getTransacciones()
                .stream()
                .filter(transaccion -> transaccion.getCategoria() != null
                        && transaccion.getCategoria().getIdCategoria().equals(categoriaId))
                .collect(Collectors.toList());
    }

    // =====================================
    // Métodos para Gestión de Presupuestos
    // =====================================

    public boolean agregarPresupuesto(String idUsuario, Presupuesto nuevoPresupuesto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return false;

        return usuario.getPresupuestos().add(nuevoPresupuesto);
    }

    public boolean actualizarPresupuesto(String idUsuario, String idPresupuesto, Presupuesto presupuestoActualizado) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return false;

        List<Presupuesto> presupuestos = usuario.getPresupuestos();
        for (int i = 0; i < presupuestos.size(); i++) {
            if (presupuestos.get(i).getIdPresupuesto().equals(idPresupuesto)) {
                presupuestos.set(i, presupuestoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarPresupuesto(String idUsuario, String idPresupuesto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return false;

        return usuario.getPresupuestos().removeIf(p -> p.getIdPresupuesto().equals(idPresupuesto));
    }

    public Presupuesto buscarPresupuesto(String idUsuario, String idPresupuesto) {
        Usuario usuario = obtenerUsuario(idUsuario);  // Obtener el usuario por id
        if (usuario != null) {
            // Si el usuario es encontrado, buscar en su lista de presupuestos
            for (Presupuesto presupuesto : usuario.getPresupuestos()) {
                if (presupuesto.getIdPresupuesto().equals(idPresupuesto)) {
                    return presupuesto;  // Retorna el presupuesto si se encuentra una coincidencia
                }
            }
        }
        return null;  // Retorna null si no se encuentra el presupuesto
    }

    public String consultarEstadoPresupuesto(String idUsuario, String idPresupuesto) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return "Usuario no encontrado";

        Presupuesto presupuesto = usuario.getPresupuestos()
                .stream()
                .filter(p -> p.getIdPresupuesto().equals(idPresupuesto))
                .findFirst()
                .orElse(null);

        if (presupuesto == null)
            return "Presupuesto no encontrado";

        double porcentajeGastado = (presupuesto.getMontoGastado() / presupuesto.getMontoTotal()) * 100;
        return String.format("Presupuesto: %s | Gastado: %.2f | Total: %.2f | %% Gastado: %.2f%%",
                presupuesto.getNombre(),
                presupuesto.getMontoGastado(),
                presupuesto.getMontoTotal(),
                porcentajeGastado);
    }

    public double calcularGastoPorCategoria(String idUsuario, String idCategoria) {
        Usuario usuario = obtenerUsuario(idUsuario);
        if (usuario == null)
            return 0;

        return usuario.getPresupuestos()
                .stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getIdCategoria().equals(idCategoria))
                .mapToDouble(Presupuesto::getMontoGastado)
                .sum();
    }

    public List<Presupuesto> devolverPresupuestos(String idUsuario){
        Usuario user = obtenerUsuario(idUsuario);
        List<Presupuesto> listaPresupuestos = user.getPresupuestos();
        return listaPresupuestos;
    }
    // =====================================
    // Métodos para Gestión de Categoria
    // =====================================
    
    // 1. Crear una nueva categoría
    public boolean crearCategoria(Categoria categoria) {
        if(categoria != null){
            categorias.add(categoria);
            System.out.println("Categoría creada: " + categoria.getNombre());
            return true;
        }else{
            return false;

        }
        
                
    }

    // 2. Asignar categoría a una transacción
    public void asignarCategoriaATransaccion(Categoria categoria, String idCategoria, String IdUsuario, String IdTransaccion) {
        Transaccion transaccion =  obtenerTransaccion(IdUsuario, IdTransaccion);
        if (categoria != null) {
            transaccion.setCategoria(categoria);
            System.out.println("Categoría " + categoria.getNombre() + " asignada a la transacción.");
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    public void asignarCategoriaAPresupuesto(Categoria categoria, String idCategoria, String idUsuario, String idPresupuesto) {
            // Buscar el presupuesto dentro de las listas del usuario
            Presupuesto presupuesto = buscarPresupuesto(idUsuario, idPresupuesto);
            if (presupuesto != null) {
                // Asignar la categoría al presupuesto
                if (categoria != null) {
                    presupuesto.setCategoria(categoria);  // Asignamos la categoría al presupuesto
                    System.out.println("Categoría " + categoria.getNombre() + " asignada al presupuesto.");
                } else {
                    System.out.println("Categoría no encontrada.");
                }
            } else {
                System.out.println("Presupuesto no encontrado.");
            }
    }

    // 3. Actualizar una categoría existente
    public void actualizarCategoria(String idCategoria, String nuevoNombre, String nuevaDescripcion) {
        Categoria categoria = buscarCategoria(idCategoria);
        if (categoria != null) {
            categoria.setNombre(nuevoNombre);
            categoria.setDescripcion(nuevaDescripcion);
            System.out.println("Categoría actualizada: " + categoria.getNombre());
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    // 4. Eliminar una categoría
    public void eliminarCategoria(String idCategoria) {
        Categoria categoria = buscarCategoria(idCategoria);
        if (categoria != null) {
            categorias.remove(categoria);
            System.out.println("Categoría eliminada: " + categoria.getNombre());
        } else {
            System.out.println("Categoría no encontrada.");
        }
    }

    // 5. Listar todas las categorías disponibles
    public void listarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías disponibles.");
        } else {
            System.out.println("Lista de Categorías:");
            for (Categoria categoria : categorias) {
                System.out.println(categoria.getNombre());
            }
        }
    }

    // Buscar categoría por ID
    public Categoria buscarCategoria(String idCategoria) {
        for (Categoria categoria : categorias) {
            if (categoria.getIdCategoria().equals(idCategoria)) {
                return categoria;
            }
        }
        return null;
    }
}
