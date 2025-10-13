package modelo;

import java.util.UUID;

/**
 * Implementación concreta de {@link Tiquete} para servicio de mensajería.
 * Aplica validaciones y cálculo de tarifa basados en peso y volumen.
 */
public class TiqueteMensajeria implements Tiquete {
    private final DatosReserva datos;
    private String codigoQR;
    private double tarifa;

    /**
     * Crea el tiquete de mensajería con los datos necesarios.
     */
    public TiqueteMensajeria(DatosReserva datos) {
        this.datos = datos;
    }

    @Override
    public void generarTiquete() {
        this.codigoQR = "ENV-" + UUID.randomUUID();
        this.tarifa = calcularTarifa();
    }

    @Override
    public void validarDatos() {
        if (datos.getNumeroGuia() == null || datos.getNumeroGuia().isBlank())
            throw new IllegalArgumentException("El número de guía es obligatorio.");
        if (datos.getPesoKg() == null || datos.getPesoKg() <= 0)
            throw new IllegalArgumentException("El peso debe ser mayor que 0.");
        if (datos.getDimensiones() == null)
            throw new IllegalArgumentException("Las dimensiones son obligatorias.");
        if (datos.getDimensiones().volumenCm3() <= 0)
            throw new IllegalArgumentException("Las dimensiones deben ser válidas (volumen > 0).");
    }

    @Override
    public String generarCodigoQR() {
        return codigoQR == null ? "QR_NO_GENERADO" : codigoQR;
    }

    @Override
    public double calcularTarifa() {
        // Regla simple: base + componente por kg + componente por volumen
        double base = 10.0;
        double porKg = 2.0 * datos.getPesoKg();
        double porVol = datos.getDimensiones().volumenCm3() / 10000.0;
        return base + porKg + porVol;
    }

    @Override
    public String emitir() {
        validarDatos();
        generarTiquete();
        return String.format(
            "TIQUETE MENSAJERÍA%nGuía: %s%nPeso: %.2f kg%nDimensiones: %.1f x %.1f x %.1f cm%nTarifa: $%.2f%nQR: %s",
            datos.getNumeroGuia(),
            datos.getPesoKg(),
            datos.getDimensiones().getLargoCm(),
            datos.getDimensiones().getAnchoCm(),
            datos.getDimensiones().getAltoCm(),
            tarifa,
            generarCodigoQR()
        );
    }
}
