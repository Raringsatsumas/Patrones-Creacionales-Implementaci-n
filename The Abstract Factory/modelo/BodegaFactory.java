package modelo;

/**
 * Abstract Factory: fábrica de una familia mínima de componentes para una bodega.
 * Crea dos productos: Inventario y Operación (que encapsula ubicación/reglas/reporte).
 */
public interface BodegaFactory {
    /** @return servicio de inventario específico de la bodega */
    InventarioService crearInventario();

    /** @return servicio de operación logística específico de la bodega */
    OperacionLogistica crearOperacion();
}
