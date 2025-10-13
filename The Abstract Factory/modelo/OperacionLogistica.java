package modelo;

/**
 * Producto 2: operación logística que agrupa políticas de ubicación, reglas y reporte.
 */
public interface OperacionLogistica {
    /** Decide/retorna una ubicación física (código de área/estantería). */
    String asignarUbicacion(Paquete p);

    /** Valida si el movimiento propuesto es permitido. */
    boolean validarMovimiento(Paquete p);

    /** Aplica el movimiento si es válido (ej.: enviar a cola/anden). */
    String aplicarMovimiento(Paquete p);

    /** Genera breve resumen/indicadores (equivalente a reporte). */
    String generarResumen();
}
