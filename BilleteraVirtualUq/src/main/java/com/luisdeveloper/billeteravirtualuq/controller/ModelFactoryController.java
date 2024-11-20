package com.luisdeveloper.billeteravirtualuq.controller;

import com.luisdeveloper.billeteravirtualuq.controller.services.IModelFactoryService;
import com.luisdeveloper.billeteravirtualuq.exceptions.*;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CategoriaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.TransaccionDto;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.UsuarioDto;
import com.luisdeveloper.billeteravirtualuq.mapping.mappers.BilleteraVirtualUqMapper;
import com.luisdeveloper.billeteravirtualuq.model.*;
import com.luisdeveloper.billeteravirtualuq.utils.Persistencia;
import com.luisdeveloper.billeteravirtualuq.utils.RabbitMQUtils;
import com.rabbitmq.client.DeliverCallback;
import com.luisdeveloper.billeteravirtualuq.utils.BilleteraVirtualUqUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ModelFactoryController implements IModelFactoryService {

    BilleteraVirtualUq billeteraVirtualUq;
    BilleteraVirtualUqMapper mapper = BilleteraVirtualUqMapper.INSTANCE;

    // ------------------------------ Singleton --------------------------------
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    BilleteraVirtualUqUtils billeteraVirtualUqUtils = new BilleteraVirtualUqUtils();

    public ModelFactoryController() {
        try {
            billeteraVirtualUq = Persistencia.cargarRecursoBinario();
            guardarDatos();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
        guardarDatos(billeteraVirtualUq);
        if (billeteraVirtualUq != null) {
            Persistencia.respaldarArchivos(billeteraVirtualUq);
        }

        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesion");

        // Iniciar el hilo consumidor para escuchar los mensajes
        iniciarConsumidorRabbitMQ();
    }

    public BilleteraVirtualUq getBilleteraVirtualUq() {
        return billeteraVirtualUq;
    }

    // Método para registrar acciones
    public void registrarAccionesSistema(String accion, int tipo, String descripcion) {
        try {
            String mensaje = String.format("Acción: %s, Tipo: %d, Descripción: %s, Fecha: %s",
                    accion, tipo, descripcion, LocalDateTime.now());

            RabbitMQUtils.getChannel().basicPublish("", "accionesSistema", null, mensaje.getBytes());
            System.out.println("Mensaje enviado a RabbitMQ: " + mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para iniciar un hilo que actúa como consumidor de RabbitMQ
    private void iniciarConsumidorRabbitMQ() {
        // Crear un hilo para el consumidor
        Thread consumidorThread = new Thread(() -> {
            try {
                // Declaramos la cola desde donde el consumidor leerá los mensajes
                RabbitMQUtils.getChannel().queueDeclare("accionesSistema", false, false, false, null);

                // Define el comportamiento cuando se reciba un mensaje de RabbitMQ
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String mensaje = new String(delivery.getBody(), "UTF-8");
                    System.out.println("Mensaje recibido de RabbitMQ: " + mensaje);

                    // Aquí puedes agregar la lógica que desees para procesar el mensaje.
                    // Por ejemplo, si el mensaje es una acción que debe ser registrada:
                    procesarMensaje(mensaje);
                };

                // Iniciar el consumo de los mensajes de la cola "accionesSistema"
                RabbitMQUtils.getChannel().basicConsume("accionesSistema", true, deliverCallback, consumerTag -> { });
                System.out.println("Consumidor de RabbitMQ iniciado.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Establecer el hilo como daemon (se cerrará cuando la aplicación termine)
        consumidorThread.setDaemon(true);
        consumidorThread.start();
    }

    // Método para procesar los mensajes (puedes personalizarlo según tus necesidades)
    private void procesarMensaje(String mensaje) {
        // Lógica para procesar el mensaje recibido
        System.out.println("Procesando mensaje: " + mensaje);
        // Ejemplo: Guardar información, registrar eventos, etc.
    }

    public void guardarDatos(BilleteraVirtualUq billeteraVirtualUq){
        if(billeteraVirtualUq != null){
            try {
                Persistencia.guardarRecursoBinario(billeteraVirtualUq);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                Persistencia.guardarRecursoXML(billeteraVirtualUq);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

    // Inicio de metodos para Usuario

    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        if (billeteraVirtualUq != null) {
            if (!billeteraVirtualUq.verificarUsuarioExistente(usuarioDto.idUsuario())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                billeteraVirtualUq.agregarUsuario(usuario);
                registrarAccionesSistema("Se agregó el usuario " + usuario.getIdUsuario(), 1, "agregarUsuario");
                return true;
            }
            registrarAccionesSistema("Intento fallido al agregar el usuario " + usuarioDto.idUsuario() + ": Ya existe",
                    2, "agregarUsuario");
            return false;
        } else {
            Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
            billeteraVirtualUq.agregarUsuario(usuario);
            registrarAccionesSistema("Se agregó el usuario " + usuario.getIdUsuario(), 1, "agregarUsuario");
            return true;
        }
    }

    public boolean actualizarUsuario(String idUsuarioActual, UsuarioDto usuarioDtoActualizado)
            throws UsuarioNoEncontradoException {
        if (billeteraVirtualUq != null) {
            Usuario usuarioActualizado = mapper.usuarioDtoToUsuario(usuarioDtoActualizado);
            boolean actualizado = billeteraVirtualUq.actualizarUsuario(idUsuarioActual, usuarioActualizado);
            if (actualizado) {
                registrarAccionesSistema("Se actualizó el usuario con ID " + idUsuarioActual, 1, "actualizarUsuario");
            } else {
                registrarAccionesSistema("No se pudo actualizar el usuario con ID " + idUsuarioActual, 3,
                        "actualizarUsuario");
            }
            return actualizado;
        }
        registrarAccionesSistema("Intento fallido de actualizar el usuario con ID " + idUsuarioActual, 2,
                "actualizarUsuario");
        return false; // Retornar false si no se pudo actualizar
    }

    public boolean eliminarUsuario(String idUsuario) throws UsuarioNoEncontradoException {
        if (billeteraVirtualUq != null) {
            boolean eliminado = billeteraVirtualUq.eliminarUsuario(idUsuario);
            if (eliminado) {
                registrarAccionesSistema("Se eliminó el usuario con ID " + idUsuario, 1, "eliminarUsuario");
            } else {
                registrarAccionesSistema("No se pudo eliminar el usuario con ID " + idUsuario, 3, "eliminarUsuario");
            }
            return eliminado;
        }
        registrarAccionesSistema("Intento fallido de eliminar el usuario con ID " + idUsuario, 2, "eliminarUsuario");
        return false; // Retornar false si no se pudo eliminar
    }

    public List<Usuario> obtenerTodosUsuarios() {
        if (billeteraVirtualUq != null) {
            // Obtener lista de usuarios y mapearla a UsuarioDto
            List<Usuario> listaUsuarios = billeteraVirtualUq.obtenerTodosUsuarios();
            registrarAccionesSistema("Consulta de todos los usuarios realizada", 1, "obtenerTodosUsuarios");
            return listaUsuarios;
        } else {
            // Manejo del caso en que billeteraVirtualUq sea null
            registrarAccionesSistema("Error al consultar todos los usuarios: billeteraVirtualUq es null", 3,
                    "obtenerTodosUsuarios");
            throw new IllegalStateException("El servicio de billetera virtual no está inicializado.");
        }
    }

    public UsuarioDto obtenerUsuario(String idUsuario) {
        if (billeteraVirtualUq != null) {
            Usuario usuario = billeteraVirtualUq.obtenerUsuario(idUsuario);
            if (usuario != null) {
                registrarAccionesSistema("Consulta del usuario con ID " + idUsuario, 1, "obtenerUsuario");
                return mapper.usuarioToUsuarioDto(usuario);
            } else {
                registrarAccionesSistema("Usuario con ID " + idUsuario + " no encontrado", 2, "obtenerUsuario");
                return null; // Retornar null si no se encuentra el usuario
            }
        }
        registrarAccionesSistema("Error al obtener el usuario con ID " + idUsuario + ": billeteraVirtualUq es null", 3,
                "obtenerUsuario");
        return null;
    }

    public boolean iniciarSesion(String Idusuario, String contrasena) {
        Usuario usuario = billeteraVirtualUq.obtenerUsuario(Idusuario);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            registrarAccionesSistema("Inicio de sesión exitoso para " + usuario.getIdUsuario(), 1, "inicioSesion");
            return true;
        } else {
            registrarAccionesSistema("Intento de inicio de sesión fallido para " + Idusuario, 2, "inicioSesion");
            return false;
        }
    }

    public double obtenerSaldo(String idUsuario) {
        Usuario usuario = billeteraVirtualUq.obtenerUsuario(idUsuario);
        if (usuario == null) {
            registrarAccionesSistema("Error al consultar saldo: Usuario no encontrado con el ID " + idUsuario, 3,
                    "obtenerSaldo");
            throw new IllegalArgumentException("Usuario no encontrado con el ID proporcionado.");
        }
        double saldo = usuario.getSaldoTotal();
        registrarAccionesSistema("Consulta de saldo realizada para el usuario " + idUsuario, 1, "obtenerSaldo");
        return saldo;
    }

    public boolean aumentarSaldo(String idUsuario, double nuevoSaldo) {
        boolean flag = billeteraVirtualUq.aumentarSaldo(idUsuario, nuevoSaldo);
        if (flag) {
            registrarAccionesSistema("Se aumentó el saldo para el usuario " + idUsuario + " en " + nuevoSaldo, 1,
                    "aumentarSaldo");
            return true;
        }
        registrarAccionesSistema("Intento fallido de aumentar saldo para el usuario " + idUsuario, 2, "aumentarSaldo");
        return false;
    }

    public boolean reducirSaldo(String idUsuario, double nuevoSaldo) {
        boolean flag = billeteraVirtualUq.reducirSaldo(idUsuario, nuevoSaldo);
        if (flag) {
            registrarAccionesSistema("Se redujo el saldo para el usuario " + idUsuario + " en " + nuevoSaldo, 1,
                    "reducirSaldo");
            return true;
        }
        registrarAccionesSistema("Intento fallido de reducir saldo para el usuario " + idUsuario, 2, "reducirSaldo");
        return false;
    }

    // =====================================
    // Métodos para Gestión de cuentas
    // =====================================

    public boolean agregarCuenta(String idUsuario) {
        UsuarioDto usuario = obtenerUsuario(idUsuario);
        if (usuario != null) {
            // Asumí que se agrega la cuenta al usuario
            registrarAccionesSistema("Se agregó una cuenta para el usuario " + idUsuario, 1, "agregarCuenta");
            return true;
        } else {
            registrarAccionesSistema("Error al agregar cuenta: Usuario no encontrado con ID " + idUsuario, 3,
                    "agregarCuenta");
            return false; // Usuario no encontrado
        }
    }

    public boolean agregarCuentasUsuario(String idUsuario, CuentaDto cuentasDto) {
        // Convertir los CuentaDto a Cuenta usando el mapper
        Cuenta cuentas = mapper.cuentaDtoToCuenta(cuentasDto);

        // Llamar al método de billeteraVirtualUq con las cuentas convertidas
        boolean resultado = billeteraVirtualUq.agregarCuentasUsuario(idUsuario, cuentas);
        if (resultado) {
            registrarAccionesSistema("Se agregó la cuenta para el usuario " + idUsuario, 1, "agregarCuentasUsuario");
        } else {
            registrarAccionesSistema("Error al agregar la cuenta para el usuario " + idUsuario, 2,
                    "agregarCuentasUsuario");
        }
        return resultado;
    }

    public CuentaDto leerCuenta(String idUsuario, String idCuenta) {
        // Obtener la cuenta desde billeteraVirtualUq
        Cuenta cuenta = billeteraVirtualUq.leerCuenta(idUsuario, idCuenta);

        if (cuenta != null) {
            registrarAccionesSistema(
                    "Consulta de cuenta realizada para el usuario " + idUsuario + " con ID de cuenta " + idCuenta, 1,
                    "leerCuenta");
            return mapper.cuentaToCuentaDto(cuenta);
        } else {
            registrarAccionesSistema("No se encontró la cuenta con ID " + idCuenta + " para el usuario " + idUsuario, 2,
                    "leerCuenta");
            return null;
        }
    }

    public boolean actualizarCuenta(String idUsuario, String idCuenta, CuentaDto cuentaDtoActualizada)
            throws UsuarioNoEncontradoException, CuentaException {
        // Convertir el CuentaDto a Cuenta
        Cuenta cuentaActualizada = mapper.cuentaDtoToCuenta(cuentaDtoActualizada);

        // Llamar al método de billeteraVirtualUq para actualizar la cuenta
        boolean actualizado = billeteraVirtualUq.actualizarCuenta(idUsuario, idCuenta, cuentaActualizada);
        if (actualizado) {
            registrarAccionesSistema(
                    "Cuenta actualizada para el usuario " + idUsuario + " con ID de cuenta " + idCuenta, 1,
                    "actualizarCuenta");
        } else {
            registrarAccionesSistema(
                    "Error al actualizar la cuenta con ID " + idCuenta + " para el usuario " + idUsuario, 2,
                    "actualizarCuenta");
        }
        return actualizado;
    }

    public void eliminarCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException, CuentaException {
        try {
            billeteraVirtualUq.eliminarCuenta(idUsuario, idCuenta);
            registrarAccionesSistema("Cuenta eliminada para el usuario " + idUsuario + " con ID de cuenta " + idCuenta,
                    1, "eliminarCuenta");
        } catch (Exception e) {
            registrarAccionesSistema("Error al eliminar la cuenta con ID " + idCuenta + " para el usuario " + idUsuario,
                    3, "eliminarCuenta");
            throw e; // Lanzar la excepción después de registrar el error
        }
    }

    public Cuenta obtenerCuenta(String idUsuario, String idCuenta) throws UsuarioNoEncontradoException {
        Cuenta cuenta = billeteraVirtualUq.obtenerCuenta(idUsuario, idCuenta);
        if (cuenta != null) {
            registrarAccionesSistema(
                    "Consulta de cuenta realizada para el usuario " + idUsuario + " con ID de cuenta " + idCuenta, 1,
                    "obtenerCuenta");
        } else {
            registrarAccionesSistema("No se encontró la cuenta con ID " + idCuenta + " para el usuario " + idUsuario, 2,
                    "obtenerCuenta");
        }
        return cuenta;
    }

    public List<Cuenta> obtenerCuentas(String idUsuario) {
        List<Cuenta> cuentas = billeteraVirtualUq.obtenerCuentas(idUsuario);
        if (cuentas != null && !cuentas.isEmpty()) {
            registrarAccionesSistema("Consulta de cuentas realizadas para el usuario " + idUsuario, 1,
                    "obtenerCuentas");
        } else {
            registrarAccionesSistema("No se encontraron cuentas para el usuario " + idUsuario, 2, "obtenerCuentas");
        }
        return cuentas;
    }

    // =====================================
    // Métodos para Gestión de Transaccion
    // =====================================

    public boolean agregarTransaccion(String idUsuario, TransaccionDto nuevaTransaccion) {
        Transaccion transaccion = mapper.transaccionDtoToTransaccion(nuevaTransaccion);
        boolean resultado = billeteraVirtualUq.agregarTransaccion(idUsuario, transaccion);
        if (resultado) {
            registrarAccionesSistema("Se agregó una nueva transacción para el usuario " + idUsuario, 1, "agregarTransaccion");
    
            // Enviar la transacción a RabbitMQ para que sea procesada por el consumidor
            RabbitMQUtils.enviarTransaccionARabbitMQ(nuevaTransaccion);  // Método que tendrás que agregar para enviar la transacción
    
        } else {
            registrarAccionesSistema("Error al agregar transacción para el usuario " + idUsuario, 2, "agregarTransaccion");
        }
        return resultado;
    }
    
    public boolean eliminarTransaccion(String idUsuario, String idTransaccion) {
        boolean resultado = billeteraVirtualUq.eliminarTransaccion(idUsuario, idTransaccion);
        if (resultado) {
            registrarAccionesSistema("Se eliminó la transacción con ID " + idTransaccion + " para el usuario " + idUsuario, 1, "eliminarTransaccion");
    
            // Enviar la eliminación a RabbitMQ si es necesario
            // Crear un DTO que indique que esta transacción fue eliminada
            RabbitMQUtils.enviarEliminacionTransaccionARabbitMQ(idUsuario, idTransaccion);  // Método para enviar información de la eliminación
        } else {
            registrarAccionesSistema("Error al eliminar la transacción con ID " + idTransaccion + " para el usuario " + idUsuario, 2, "eliminarTransaccion");
        }
        return resultado;
    }

public boolean actualizarTransaccion(String idUsuario, String idTransaccion, Transaccion transaccionActualizada) {
    boolean resultado = billeteraVirtualUq.actualizarTransaccion(idUsuario, idTransaccion, transaccionActualizada);
    if (resultado) {
        registrarAccionesSistema("Se actualizó la transacción con ID " + idTransaccion + " para el usuario " + idUsuario, 1, "actualizarTransaccion");

        // Enviar la transacción actualizada a RabbitMQ para que sea procesada por el consumidor
    } else {
        registrarAccionesSistema("Error al actualizar la transacción con ID " + idTransaccion + " para el usuario " + idUsuario, 2, "actualizarTransaccion");
    }
    return resultado;
}

    public Transaccion obtenerTransaccion(String idUsuario, String idTransaccion) {
        Transaccion transaccion = billeteraVirtualUq.obtenerTransaccion(idUsuario, idTransaccion);
        if (transaccion != null) {
            registrarAccionesSistema("Consulta realizada para obtener la transacción con ID " + idTransaccion
                    + " para el usuario " + idUsuario, 1, "obtenerTransaccion");
        } else {
            registrarAccionesSistema(
                    "No se encontró la transacción con ID " + idTransaccion + " para el usuario " + idUsuario, 2,
                    "obtenerTransaccion");
        }
        return transaccion;
    }

    public List<Transaccion> listarTransacciones(String idUsuario) {
        List<Transaccion> transacciones = billeteraVirtualUq.listarTransacciones(idUsuario);
        if (transacciones != null && !transacciones.isEmpty()) {
            registrarAccionesSistema("Consulta realizada para listar las transacciones del usuario " + idUsuario, 1,
                    "listarTransacciones");
        } else {
            registrarAccionesSistema("No se encontraron transacciones para el usuario " + idUsuario, 2,
                    "listarTransacciones");
        }
        return transacciones;
    }

    public List<Transaccion> filtrarTransaccionesPorFecha(String idUsuario, LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {
        List<Transaccion> transacciones = billeteraVirtualUq.filtrarTransaccionesPorFecha(idUsuario, fechaInicio,
                fechaFin);
        if (transacciones != null && !transacciones.isEmpty()) {
            registrarAccionesSistema(
                    "Consulta realizada para filtrar transacciones por fecha para el usuario " + idUsuario, 1,
                    "filtrarTransaccionesPorFecha");
        } else {
            registrarAccionesSistema(
                    "No se encontraron transacciones en el rango de fechas para el usuario " + idUsuario, 2,
                    "filtrarTransaccionesPorFecha");
        }
        return transacciones;
    }

    public List<Transaccion> filtrarTransaccionesPorTipo(String idUsuario, String tipoTransaccion) {
        List<Transaccion> transacciones = billeteraVirtualUq.filtrarTransaccionesPorTipo(idUsuario, tipoTransaccion);
        if (transacciones != null && !transacciones.isEmpty()) {
            registrarAccionesSistema(
                    "Consulta realizada para filtrar transacciones por tipo para el usuario " + idUsuario, 1,
                    "filtrarTransaccionesPorTipo");
        } else {
            registrarAccionesSistema(
                    "No se encontraron transacciones de tipo " + tipoTransaccion + " para el usuario " + idUsuario, 2,
                    "filtrarTransaccionesPorTipo");
        }
        return transacciones;
    }

    public List<Transaccion> filtrarTransaccionesPorCategoria(String idUsuario, String categoriaId) {
        List<Transaccion> transacciones = billeteraVirtualUq.filtrarTransaccionesPorCategoria(idUsuario, categoriaId);
        if (transacciones != null && !transacciones.isEmpty()) {
            registrarAccionesSistema(
                    "Consulta realizada para filtrar transacciones por categoría para el usuario " + idUsuario, 1,
                    "filtrarTransaccionesPorCategoria");
        } else {
            registrarAccionesSistema("No se encontraron transacciones para la categoría " + categoriaId
                    + " para el usuario " + idUsuario, 2, "filtrarTransaccionesPorCategoria");
        }
        return transacciones;
    }

    // =====================================
    // Métodos para Gestión de Presupuestos
    // =====================================

    public boolean agregarPresupuesto(String idUsuario, Presupuesto nuevoPresupuesto) {
        // Agregar presupuesto y registrar acción
        boolean resultado = billeteraVirtualUq.agregarPresupuesto(idUsuario, nuevoPresupuesto);
        if (resultado) {
            registrarAccionesSistema("Se agregó un nuevo presupuesto para el usuario " + idUsuario, 1,
                    "agregarPresupuesto");
        } else {
            registrarAccionesSistema("Error al agregar presupuesto para el usuario " + idUsuario, 2,
                    "agregarPresupuesto");
        }
        return resultado;
    }

    public boolean actualizarPresupuesto(String idUsuario, String idPresupuesto, Presupuesto presupuestoActualizado) {
        // Actualizar presupuesto y registrar acción
        boolean resultado = billeteraVirtualUq.actualizarPresupuesto(idUsuario, idPresupuesto, presupuestoActualizado);
        if (resultado) {
            registrarAccionesSistema(
                    "Se actualizó el presupuesto con ID " + idPresupuesto + " para el usuario " + idUsuario, 1,
                    "actualizarPresupuesto");
        } else {
            registrarAccionesSistema(
                    "Error al actualizar el presupuesto con ID " + idPresupuesto + " para el usuario " + idUsuario, 2,
                    "actualizarPresupuesto");
        }
        return resultado;
    }

    public boolean eliminarPresupuesto(String idUsuario, String idPresupuesto) {
        // Eliminar presupuesto y registrar acción
        boolean resultado = billeteraVirtualUq.eliminarPresupuesto(idUsuario, idPresupuesto);
        if (resultado) {
            registrarAccionesSistema(
                    "Se eliminó el presupuesto con ID " + idPresupuesto + " para el usuario " + idUsuario, 1,
                    "eliminarPresupuesto");
        } else {
            registrarAccionesSistema(
                    "Error al eliminar el presupuesto con ID " + idPresupuesto + " para el usuario " + idUsuario, 2,
                    "eliminarPresupuesto");
        }
        return resultado;
    }

    public Presupuesto buscarPresupuesto(String idUsuario, String idPresupuesto) {
        // Buscar presupuesto y registrar acción
        Presupuesto presupuesto = billeteraVirtualUq.buscarPresupuesto(idUsuario, idPresupuesto);
        if (presupuesto != null) {
            registrarAccionesSistema("Consulta realizada para obtener el presupuesto con ID " + idPresupuesto
                    + " para el usuario " + idUsuario, 1, "buscarPresupuesto");
        } else {
            registrarAccionesSistema(
                    "No se encontró el presupuesto con ID " + idPresupuesto + " para el usuario " + idUsuario, 2,
                    "buscarPresupuesto");
        }
        return presupuesto;
    }

    public String consultarEstadoPresupuesto(String idUsuario, String idPresupuesto) {
        // Consultar estado del presupuesto y registrar acción
        String estado = billeteraVirtualUq.consultarEstadoPresupuesto(idUsuario, idPresupuesto);
        if (estado != null) {
            registrarAccionesSistema("Consulta realizada para consultar el estado del presupuesto con ID "
                    + idPresupuesto + " para el usuario " + idUsuario, 1, "consultarEstadoPresupuesto");
        } else {
            registrarAccionesSistema("No se encontró el estado del presupuesto con ID " + idPresupuesto
                    + " para el usuario " + idUsuario, 2, "consultarEstadoPresupuesto");
        }
        return estado;
    }

    public double calcularGastoPorCategoria(String idUsuario, String idCategoria) {
        // Calcular gasto por categoría y registrar acción
        double gasto = billeteraVirtualUq.calcularGastoPorCategoria(idUsuario, idCategoria);
        registrarAccionesSistema("Consulta realizada para calcular el gasto por categoría para el usuario " + idUsuario
                + " en la categoría " + idCategoria, 1, "calcularGastoPorCategoria");
        return gasto;
    }

    public List<Presupuesto> devolverPresupuestos(String idUsuario){
        return billeteraVirtualUq.devolverPresupuestos(idUsuario);
    }
    // =====================================
    // Métodos para Gestión de Categoria
    // =====================================

    // 1. Crear una nueva categoría
    public boolean crearCategoria(CategoriaDto categoriaDTO) {
        // Convertir CategoriaDto a Categoria y agregar la nueva categoría
        Categoria categoria = mapper.categoriaDtoToCategoria(categoriaDTO);
        boolean resultado = billeteraVirtualUq.crearCategoria(categoria);
        if (resultado) {
            registrarAccionesSistema("Se creó una nueva categoría: " + categoria.getNombre(), 1, "crearCategoria");
        } else {
            registrarAccionesSistema("Error al crear la categoría: " + categoria.getNombre(), 2, "crearCategoria");
        }
        return resultado;
    }

    public void asignarCategoriaATransaccion(Categoria categoria, String idCategoria, String idUsuario,
            String idTransaccion) {
        // Asignar categoría a la transacción y registrar acción
        billeteraVirtualUq.asignarCategoriaATransaccion(categoria, idCategoria, idUsuario, idTransaccion);
        registrarAccionesSistema("Se asignó la categoría " + categoria.getNombre() + " a la transacción "
                + idTransaccion + " del usuario " + idUsuario, 1, "asignarCategoriaATransaccion");
    }

    public void actualizarCategoria(String idCategoria, String nuevoNombre, String nuevaDescripcion) {
        // Actualizar categoría y registrar acción
        billeteraVirtualUq.actualizarCategoria(idCategoria, nuevoNombre, nuevaDescripcion);
        registrarAccionesSistema("Se actualizó la categoría con ID " + idCategoria + " a nombre: " + nuevoNombre, 1,
                "actualizarCategoria");
    }

    public void eliminarCategoria(String idCategoria) {
        // Eliminar categoría y registrar acción
        billeteraVirtualUq.eliminarCategoria(idCategoria);
        registrarAccionesSistema("Se eliminó la categoría con ID " + idCategoria, 1, "eliminarCategoria");
    }

    public void listarCategorias() {
        // Listar todas las categorías y registrar acción
        billeteraVirtualUq.listarCategorias();
        registrarAccionesSistema("Se consultaron todas las categorías", 1, "listarCategorias");
    }

    public Categoria buscarCategoria(String idCategoria){
        return billeteraVirtualUq.buscarCategoria(idCategoria);
    }

    public void setBilleteraVirtualUq(BilleteraVirtualUq billeteraVirtualUq) {
        this.billeteraVirtualUq = billeteraVirtualUq;
    }

    public void guardarDatos() throws Exception{
        Persistencia.guardarRecursoBinario(billeteraVirtualUq);
    }
}
