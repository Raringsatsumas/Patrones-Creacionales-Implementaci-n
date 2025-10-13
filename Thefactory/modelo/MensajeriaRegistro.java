package modelo;

/**
 * Estructura de datos para persistir un envío de mensajería en memoria.
 */
public class MensajeriaRegistro {
    public String numeroGuia;
    public double peso;
    public Dimensiones dim;

    public MensajeriaRegistro(String numeroGuia, double peso, Dimensiones dim) {
        this.numeroGuia = numeroGuia; this.peso = peso; this.dim = dim;
    }
}
