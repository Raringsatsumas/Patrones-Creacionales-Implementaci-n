package modelo;

/**
 * Producto 1: servicio de inventario para registrar entradas, salidas y actualizar stock.
 * Ahora incluye actualizar y eliminar (CRUD simple en memoria).
 */
public interface InventarioService {
    String registrarIngreso(Paquete p);
    String registrarSalida(Paquete p);
    String actualizarStock();

    /** Actualiza los datos básicos almacenados del paquete (peso/volumen). */
    String actualizarRegistro(Paquete p);

    /** Elimina el registro almacenado por código (si existe). */
    String eliminarRegistro(String codigo);
}
