package controlador;

import modelo.*;

/**
 * pasajeros y mensajería. Además aloja validaciones cruzadas que la Vista
 * puede reutilizar solo nombre solo letras
 */
public class SistemaReservas {
    /** Fábrica que decide qué implementación concreta de {@link Tiquete} instanciar. */
    private final TiqueteFactory factory = new TiqueteFactory();

    /** Repositorios en memoria para persistencia simple. */
    private final RepositorioPasajeros repoPax = new RepositorioPasajeros();
    private final RepositorioEnvios repoEnv = new RepositorioEnvios();

    // EMISIÓN DE TIQUETES 
    /**
     * Crea y emite un tiquete (pasajero o mensajería) según el tipo en {@code datos}.
     * La validación de campos específicos se hace en cada clase concreta del Modelo.
     * @param datos DTO con la información capturada desde la Vista
     * @return texto renderizado del tiquete emitido (incluye cálculo de tarifa y “código QR”)
     * @throws IllegalArgumentException si el tipo no es soportado o si los datos fallan validación
     */
    public String procesarReserva(DatosReserva datos) {
        Tiquete t = factory.crearTiquete(datos.getTipoServicio(), datos);
        return t.emitir();
    }

    //VALIDACIONES COMPARTIDAS 

    /**
     * Verifica que el nombre contenga solo letras (incluye acentos) y espacios.
     *
     * @param nombre nombre del pasajero
     * @throws IllegalArgumentException si está vacío o contiene caracteres inválidos
     */
    public void validarNombreSoloLetras(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del pasajero es obligatorio.");
        }
        // A-Z + Ñ/ñ + vocales con acentos + espacios
        if (!nombre.matches("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras y espacios.");
        }
    }

    //  CRUD PASAJEROS 

    /**
     * Crea un registro de pasajero en memoria.
     */
    public void crearPasajero(String nombre, Integer asiento, String ruta, java.time.LocalDate fecha) {
        validarNombreSoloLetras(nombre);
        repoPax.crear(new PasajeroRegistro(nombre.trim(), asiento, ruta, fecha));
    }

    /**
     * Actualiza un registro de pasajero en memoria (debe existir).
     */
    public void actualizarPasajero(String nombre, Integer asiento, String ruta, java.time.LocalDate fecha) {
        validarNombreSoloLetras(nombre);
        repoPax.actualizar(new PasajeroRegistro(nombre.trim(), asiento, ruta, fecha));
    }

    /**
     * Elimina un registro de pasajero por nombre.
     */
    public void eliminarPasajero(String nombre) {
        repoPax.eliminar(nombre.trim());
    }

    /**
     * Consulta si existe un pasajero por nombre.
     */
    public boolean existePasajero(String nombre) {
        return repoPax.existe(nombre.trim());
    }

    // CRUD MENSAJERÍA 

    /**
     * Crea un envío (mensajería) en memoria.
     */
    public void crearEnvio(String guia, double peso, Dimensiones dim) {
        if (guia == null || guia.isBlank()) throw new IllegalArgumentException("El número de guía es obligatorio.");
        repoEnv.crear(new MensajeriaRegistro(guia.trim(), peso, dim));
    }

    /**
     * Actualiza un envío (debe existir previamente).
     */
    public void actualizarEnvio(String guia, double peso, Dimensiones dim) {
        if (guia == null || guia.isBlank()) throw new IllegalArgumentException("El número de guía es obligatorio.");
        repoEnv.actualizar(new MensajeriaRegistro(guia.trim(), peso, dim));
    }

    /**
     * Elimina un envío por número de guía.
     */
    public void eliminarEnvio(String guia) {
        repoEnv.eliminar(guia.trim());
    }

    /**
     * Consulta si existe un envío por número de guía.
     */
    public boolean existeEnvio(String guia) {
        return repoEnv.existe(guia.trim());
    }
}
