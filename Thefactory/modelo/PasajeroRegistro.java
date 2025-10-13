package modelo;

import java.time.LocalDate;

/**
 * Estructura de datos para persistir un pasajero en memoria.
 * Se usa en el repositorio de pasajeros únicamente como uso.
 */
public class PasajeroRegistro {
    public String nombre;
    public Integer asiento;
    public String ruta;
    public LocalDate fecha;

    public PasajeroRegistro(String nombre, Integer asiento, String ruta, LocalDate fecha) {
        this.nombre = nombre; this.asiento = asiento; this.ruta = ruta; this.fecha = fecha;
    }
}
