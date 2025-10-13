package modelo;

/**
 * Valor-objeto que representa las dimensiones de un paquete (en centímetros).
 */
public class Dimensiones {
    private double largoCm;
    private double anchoCm;
    private double altoCm;

    /** Constructor vacío para frameworks o uso gradual. */
    public Dimensiones() {}

    /**
     * Constructor
     *
     * @param largoCm largo en cm
     * @param anchoCm ancho en cm
     * @param altoCm  alto en cm
     */
    public Dimensiones(double largoCm, double anchoCm, double altoCm) {
        this.largoCm = largoCm; this.anchoCm = anchoCm; this.altoCm = altoCm;
    }

    public double getLargoCm() { return largoCm; }
    public void setLargoCm(double largoCm) { this.largoCm = largoCm; }
    public double getAnchoCm() { return anchoCm; }
    public void setAnchoCm(double anchoCm) { this.anchoCm = anchoCm; }
    public double getAltoCm() { return altoCm; }
    public void setAltoCm(double altoCm) { this.altoCm = altoCm; }

    /**
     * @return volumen en centímetros cúbicos (cm³)
     */
    public double volumenCm3() { return largoCm * anchoCm * altoCm; }
}
