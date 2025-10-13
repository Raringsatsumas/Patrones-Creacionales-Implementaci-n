package modelo;

/**
 * Implementación del patrón Factory.
 * Esta clase concentra el conocimiento para decidir cuál clase concreta de
 * {@link Tiquete} crear (pasajero o mensajería) a partir del tipo recibido.
 * De este modo, la Vista/Controlador quedan desacoplados de las clases concretas.
 */
public class TiqueteFactory {

    /**
     * Indica si el tipo es soportado por esta fábrica.
     */
    public boolean validarTipoServicio(String tipo) {
        return "PASAJERO".equalsIgnoreCase(tipo) || "MENSAJERIA".equalsIgnoreCase(tipo);
    }

    /**
     * Crea el producto concreto de tiquete según el tipo.
     * @param tipoServicio "PASAJERO" o "MENSAJERIA"
     * @param datos        DTO con la información de entrada
     * @return implementación concreta de {@link Tiquete}
     * @throws IllegalArgumentException si el tipo no está soportado
     */
    public Tiquete crearTiquete(String tipoServicio, DatosReserva datos) {
        if (!validarTipoServicio(tipoServicio)) {
            throw new IllegalArgumentException("Tipo de servicio no soportado: " + tipoServicio);
        }
        if ("PASAJERO".equalsIgnoreCase(tipoServicio)) {
            return new TiquetePasajeroEstandar(datos);
        } else {
            return new TiqueteMensajeria(datos);
        }
    }
}
