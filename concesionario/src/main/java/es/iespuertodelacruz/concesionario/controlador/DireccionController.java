package es.iespuertodelacruz.concesionario.controlador;

import es.iespuertodelacruz.concesionario.api.Direccion;
import es.iespuertodelacruz.concesionario.exception.BbddException;
import es.iespuertodelacruz.concesionario.exception.DireccionException;
import es.iespuertodelacruz.concesionario.exception.PersistenciaException;
import es.iespuertodelacruz.concesionario.modelo.DireccionModelo;

/**
 * Clase DireccionController
 */
public class DireccionController {
    DireccionModelo direccionModelo;

    /**
     * Constructor de la clase DireccionController
     * @throws PersistenciaException
     */
    public DireccionController() throws PersistenciaException {
        direccionModelo = new DireccionModelo();
    }


    /**
     * Metodo encargado de realizar la validacion del objeto Direccion
     * @param direccion direccion a validar
     * @throws DireccionException error controlado
     */
    public void validarDireccion(Direccion direccion) throws DireccionException {
        String mensaje = "";

        if (direccion == null) {
            mensaje = "Se esta intentando validar un objeto vacio";
            throw new DireccionException(mensaje);
        }
        if (direccion.getNumero() <= 0) {
            mensaje = "El numero de domicilio es 0 o menor\n";
        }
        if (direccion.getCalle() == null || direccion.getCalle().isEmpty()) {
            mensaje += "La calle es nula o vacia\n";
        }
        if (direccion.getCodigoPostal() == null || direccion.getCodigoPostal().isEmpty()) {
            mensaje += "El codigo postal es nulo o vacio\n";
        }
        if (direccion.getCiudad() == null || direccion.getCiudad().isEmpty()) {
            mensaje += "La ciudad es nula o vacia\n";
        }
        if (direccion.getProvincia() == null || direccion.getProvincia().isEmpty()) {
            mensaje += "La provincia es nula o vacia";
        }
        if (!mensaje.isEmpty()) {
            throw new DireccionException(mensaje);
        }
    }

    /**
     * Metodo encargado de insertar
     * @param direccion direccion a insertar
     * @throws DireccionException error controlado
     * @throws PersistenciaException
     */
    public void insertar(Direccion direccion) throws DireccionException, PersistenciaException {
        validarDireccion(direccion);
        direccionModelo.insertar(direccion);
    }

    /**
     * Metodo encargado de eliminar
     * @param direccion direccion a eliminar
     * @throws DireccionException error controlado
     * @throws PersistenciaException
     */
    public void eliminar(Direccion direccion) throws DireccionException, PersistenciaException {
        validarDireccion(direccion);
        direccionModelo.eliminar(direccion);
    }

    /**
     * Metodo encargado de eliminar utilizando el identificador de la direccion
     * @param dni dni del cliente
     * @throws DireccionException error controlado
     * @throws PersistenciaException
     */
    public void eliminar(String identificador) throws  DireccionException, PersistenciaException {
        Direccion direccion;
        direccion = buscar(identificador);
        eliminar(direccion);
    }

    /**
     * Metodo encargado de modificar
     * @param direccion direccion a modificar
     * @throws DireccionException error controlado
     * @throws PersistenciaException
     */
    public void modificar(Direccion direccion) throws DireccionException, PersistenciaException {
        Direccion direccionAlmacenada;
        validarDireccion(direccion);
        direccionAlmacenada = buscar(direccion.getIdentificador());
        if (direccionAlmacenada == null) {
            throw new DireccionException("La direccion indicada no existe");
        }
        direccionModelo.actualizar(direccion);
    }

    /**
     * Metodo encargado de buscar por codigo de cliente
     * @param dni dni del cliente
     * @return cliente encontrado
     * @throws PersistenciaException
     */
    public Direccion buscar(String identificador) throws PersistenciaException  {
        Direccion direccion = null;
        direccion = direccionModelo.buscar(identificador);
        return direccion;
    }

    /**
     * Funcion encargada de verificar si existe o no una direccion
     * @param direccion direccion a encontrar
     * @return true/false si existe o no
     */
    public boolean existe(Direccion direccion) {
        boolean encontrado = false;
        Direccion direccionEncontrada;

        direccionEncontrada = null; //buscar(direccion.PK);
        if (direccionEncontrada != null) {
            encontrado = true;
        }

        return encontrado;
    }
}
