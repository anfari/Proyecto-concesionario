package es.iespuertodelacruz.concesionario.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.iespuertodelacruz.concesionario.api.Cliente;
import es.iespuertodelacruz.concesionario.api.Direccion;
import es.iespuertodelacruz.concesionario.api.Persona;
import es.iespuertodelacruz.concesionario.controlador.*;
import es.iespuertodelacruz.concesionario.exception.*;

public class ClienteControllerTest {
    ClienteController clienteController;
    PersonaController personaController;
    DireccionController direccionController;
    Cliente cliente;
    Persona persona;
    Direccion direccion;

    @BeforeEach 
    public void setUp(){
        if (clienteController == null){
            try {
                clienteController = new ClienteController(true);
            } catch (PersistenciaException e) {
                fail("Error al inicializar el modelo", e);
            }
        }
        if (personaController == null){
            try {
                personaController = new PersonaController(true);
            } catch (PersistenciaException e) {
                fail("Error al inicializar el modelo", e);
            }
        }
        if (direccionController == null){
            try {
                direccionController = new DireccionController(true);
            } catch (PersistenciaException e) {
                fail("Error al inicializar el modelo", e);
            }
        }
        cliente = crearCliente();
        try {
            clienteController.insertar(cliente);
        } catch (PersistenciaException | ClienteException e) {
            fail("Error al insertar el cliente", e);
        }
        persona = crearPersona();
        try {
            personaController.insertar(persona);
        } catch (PersistenciaException | PersonaException e) {
            fail("Error al insertar la persona", e);
        }
        direccion = crearDireccion();
        try {
            direccionController.insertar(direccion);
        } catch (PersistenciaException | DireccionException e) {
            fail("Error al insertar la direccion", e);
        }
    }

    @AfterEach 
    public void tearDown(){
        try {
            clienteController.eliminar(cliente);
        } catch (PersistenciaException | ClienteException e) {
            fail("Error al eliminar el cliente", e);
        }
        try {
            personaController.eliminar(persona);
        } catch (PersistenciaException | PersonaException e) {
            fail("Error al eliminar la persona", e);
        }
        try {
            direccionController.eliminar(direccion);
        } catch (PersistenciaException | DireccionException e) {
            fail("Error al eliminar la direccion", e);
        }
    }


    @Test
    public void constructorTest() {
        try {
            clienteController = new ClienteController();
            clienteController = new ClienteController(true);
        } catch (PersistenciaException e) {
            fail("Error al inicialziar el modelo");
        }
    }

    @Test
    public void validarClienteNuloTest() {
        Cliente clienteNulo = null;

        try {
            clienteController.validarCliente(clienteNulo);
        } catch (ClienteException e) {
            assertTrue(e.getMessage().contains("Se esta intentando validar un objeto vacio"));
        }

    }

    @Test
    public void validarClienteVacioTest() {
        Cliente clienteVacio = new Cliente("", "", "", "", "", "", null);

        try {
            clienteController.validarCliente(clienteVacio);
        } catch (ClienteException e) {
            assertTrue(e.getMessage().contains("El telefono"));
        }
    }

    @Test
    public void validarClienteNullTest() {
        Cliente clienteVacio = new Cliente(null, null, null, null, null, null, null);

        try {
            clienteController.validarCliente(clienteVacio);
        } catch (ClienteException e) {
            assertTrue(e.getMessage().contains("del cliente"));
        }
    }


    @Test
    public void insertarErrorTest() {
        try {
            clienteController.insertar(cliente);
        } catch (PersistenciaException | ClienteException e) {
            assertTrue(e.getMessage().contains("El cliente indicado ya existe"));
        }
    }

    @Test
    public void eliminarErrorTest() {
        Cliente clienteBorrar = crearCliente2();
        try {
            clienteController.eliminar(clienteBorrar);
        } catch (PersistenciaException | ClienteException e) {
            assertTrue(e.getMessage().contains("El cliente indicado no existe"));
        }
    }

    @Test
    public void eliminarDniTest() {
        try {
            clienteController.eliminar("55555555H");
        } catch (PersistenciaException | ClienteException e) {
            fail("Error al eliminar el cliente", e);
        }
        try {
            clienteController.insertar(cliente);
        } catch (PersistenciaException | ClienteException e) {
            fail("Error al insertar el cliente", e);
        }
    }

    @Test
    public void modificarTest() {
        cliente.setNombre("Benito");
        try {
            clienteController.modificar(cliente);
        } catch (PersistenciaException | ClienteException e) {
            fail("Error al actualizar el cliente", e);
        }
    }

    @Test
    public void modificarErrorTest() {
        Cliente clienteModificar = new Cliente(null, "Jose", "Ramos", "77777777B", "15/08/2020", "123456789", direccion);
        try {
            clienteController.modificar(clienteModificar);
        } catch (PersistenciaException | ClienteException e) {
            assertTrue(e.getMessage().contains("El cliente indicado no existe"));
        }
    }

    @Test
    public void listadoClientesTest() {
        try {
            assertEquals(11, clienteController.listadoClientes().size(), "El tamanio no es el esperado");
        } catch (PersistenciaException e) {
            fail("Error al listar los clientes", e);
        }
    }



    /**
     * Funcion encargado de crear una persona para test
     * @return persona creada
     */
    private Persona crearPersona() {
        return new Persona("Juan", "Perez", "55555555H", "15/05/1992", "123456789", crearDireccion());
    }

    /**
     * Funcion encargado de crear un cliente para test
     * @return cliente creado
     */
    private Cliente crearCliente() {
        return new Cliente(null, "Juan", "Perez", "55555555H", "15/05/1992", "123456789", crearDireccion());
    }

    /**
     * Funcion encargado de crear un cliente para test
     * @return cliente creado
     */
    private Cliente crearCliente2() {
        return new Cliente(null, "Jose", "Gil", "12345678J", "12/01980", "123456789", crearDireccion());
    }

    /**
     * Funcion encargado de crear una direccion para test
     * @return direccion creada
     */
    private Direccion crearDireccion() {
        return new Direccion("55555555H", "Camino Dia", 3, "38400", "Santa Cruz de Tenerife", "Puerto de la Cruz", "Espana");
    }
    
}
