package modelo;

import java.time.LocalDate;

/**
 * DTO de entrada para la fábrica. Agrega los datos necesarios para construir
 * cualquiera de los tiquetes soportados (pasajero/mensajería).
 */
public class DatosReserva {
    /** Tipo de servicio: "PASAJERO" o "MENSAJERIA". */
    private String tipoServicio;

    //  Pasajero 
    private String ruta;
    private LocalDate fechaViaje;
    private Integer numeroAsiento;
    private String pasajeroNombre;

    // Mensajería 
    private String numeroGuia;
    private Double pesoKg;
    private Dimensiones dimensiones;

    // Getters/Setters
    public String getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(String tipoServicio) { this.tipoServicio = tipoServicio; }
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    public LocalDate getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(LocalDate fechaViaje) { this.fechaViaje = fechaViaje; }
    public Integer getNumeroAsiento() { return numeroAsiento; }
    public void setNumeroAsiento(Integer numeroAsiento) { this.numeroAsiento = numeroAsiento; }
    public String getPasajeroNombre() { return pasajeroNombre; }
    public void setPasajeroNombre(String pasajeroNombre) { this.pasajeroNombre = pasajeroNombre; }
    public String getNumeroGuia() { return numeroGuia; }
    public void setNumeroGuia(String numeroGuia) { this.numeroGuia = numeroGuia; }
    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public Dimensiones getDimensiones() { return dimensiones; }
    public void setDimensiones(Dimensiones dimensiones) { this.dimensiones = dimensiones; }
}
