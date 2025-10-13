package modelo;

/**
 * Interfaz del Producto en el patrón Factory.
 * Define el contrato que deben cumplir todos los tipos de tiquete.
 */
public interface Tiquete {
    /** Prepara datos internos del tiquete. */
    void generarTiquete();

    /** Valida campos de negocio específicos del tipo de tiquete. */
    void validarDatos();

    /** Devuelve el código QR generado. */
    String generarCodigoQR();

    /** Calcula la tarifa del servicio según sus reglas particulares. */
    double calcularTarifa();

    String emitir();
}
