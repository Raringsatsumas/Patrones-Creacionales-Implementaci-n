package modelo;

/**
 * Entidad de dominio: paquete dentro del flujo logístico.
 */
public class Paquete {
    private final String codigo;
    private final double pesoKg;
    private final double volumenCm3;

    /**
     * @param codigo identificador único del paquete
     * @param pesoKg peso en kilogramos (debe ser > 0)
     * @param volumenCm3 volumen en centímetros cúbicos (debe ser > 0)
     */
    public Paquete(String codigo, double pesoKg, double volumenCm3) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código vacío");
        if (pesoKg <= 0) throw new IllegalArgumentException("Peso inválido");
        if (volumenCm3 <= 0) throw new IllegalArgumentException("Volumen inválido");
        this.codigo = codigo;
        this.pesoKg = pesoKg;
        this.volumenCm3 = volumenCm3;
    }

    public String getCodigo()     { return codigo; }
    public double getPesoKg()     { return pesoKg; }
    public double getVolumenCm3() { return volumenCm3; }
}
