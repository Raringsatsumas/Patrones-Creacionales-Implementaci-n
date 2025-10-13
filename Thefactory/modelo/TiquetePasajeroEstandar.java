package modelo;

import java.util.UUID;

/**
 * Implementación concreta de {@link Tiquete} para pasajeros estándar.
 * Encapsula validaciones propias del dominio de pasajeros y el cálculo de tarifa.
 */
public class TiquetePasajeroEstandar implements Tiquete {
    /** Datos de entrada provistos por la Vista/Controlador. */
    private final DatosReserva datos;

    /** Estado interno generado durante la emisión. */
    private String codigoQR;
    private double tarifa;

    /**
     * Crea el tiquete de pasajero con los datos necesarios.
     * @param datos DTO con los valores capturados en la UI
     */
    public TiquetePasajeroEstandar(DatosReserva datos) {
        this.datos = datos;
    }

    @Override
    public void generarTiquete() {
        // Generación simple de “QR”.
        this.codigoQR = "PAX-" + UUID.randomUUID();
        this.tarifa = calcularTarifa();
    }

    @Override
    public void validarDatos() {
        if (datos.getPasajeroNombre() == null || datos.getPasajeroNombre().isBlank())
            throw new IllegalArgumentException("El nombre del pasajero es obligatorio.");
        if (datos.getNumeroAsiento() == null || datos.getNumeroAsiento() <= 0)
            throw new IllegalArgumentException("El número de asiento debe ser mayor que 0.");
        if (datos.getRuta() == null || datos.getRuta().isBlank())
            throw new IllegalArgumentException("La ruta es obligatoria.");
        if (datos.getFechaViaje() == null)
            throw new IllegalArgumentException("La fecha de viaje es obligatoria (yyyy-MM-dd).");
    }

    @Override
    public String generarCodigoQR() {
        return codigoQR == null ? "QR_NO_GENERADO" : codigoQR;
    }

    @Override
    public double calcularTarifa() {
        // Regla simple para ejemplo: base + recargo según longitud de la ruta (tope)
        double base = 20.0;
        double recargo = Math.min(30.0, (datos.getRuta() != null ? datos.getRuta().length() : 0) * 0.5);
        return base + recargo;
    }

    @Override
    public String emitir() {
        validarDatos();
        generarTiquete();
        return String.format(
            "TIQUETE PASAJERO%nPasajero: %s%nAsiento: %d%nRuta: %s%nFecha: %s%nTarifa: $%.2f%nQR: %s",
            datos.getPasajeroNombre(),
            datos.getNumeroAsiento(),
            datos.getRuta(),
            String.valueOf(datos.getFechaViaje()),
            tarifa,
            generarCodigoQR()
        );
    }
}
