package modelo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Producto final del patrón Builder: viaje completamente configurado.
 */
public class ViajeProgramado {
    private String id;
    private Ruta ruta;
    private Vehiculo vehiculo;
    private Conductor conductorPrincipal;
    private LocalDateTime fechaHoraSalida;
    private LocalDateTime llegadaEstimada;
    private int capacidadAsignada;
    private List<String> paradasOpcionales;
    private String politicasEquipaje;
    private List<String> serviciosAdicionales;
    private double tarifa;

    // --- Setters (solo Builder los usa) ---
    void setId(String id) { this.id = id; }
    void setRuta(Ruta ruta) { this.ruta = ruta; }
    void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    void setConductorPrincipal(Conductor cp) { this.conductorPrincipal = cp; }
    void setFechaHoraSalida(LocalDateTime salida) { this.fechaHoraSalida = salida; }
    void setLlegadaEstimada(LocalDateTime llegada) { this.llegadaEstimada = llegada; }
    void setCapacidadAsignada(int cap) { this.capacidadAsignada = cap; }
    void setParadasOpcionales(List<String> paradas) { this.paradasOpcionales = paradas; }
    void setPoliticasEquipaje(String politicasEquipaje) { this.politicasEquipaje = politicasEquipaje; }
    void setServiciosAdicionales(List<String> serviciosAdicionales) { this.serviciosAdicionales = serviciosAdicionales; }
    void setTarifa(double tarifa) { this.tarifa = tarifa; }

    // Getters 
    public String getId() { return id; }
    public Ruta getRuta() { return ruta; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public Conductor getConductorPrincipal() { return conductorPrincipal; }
    public LocalDateTime getFechaHoraSalida() { return fechaHoraSalida; }
    public LocalDateTime getLlegadaEstimada() { return llegadaEstimada; }
    public int getCapacidadAsignada() { return capacidadAsignada; }
    public List<String> getParadasOpcionales() { return paradasOpcionales; }
    public String getPoliticasEquipaje() { return politicasEquipaje; }
    public List<String> getServiciosAdicionales() { return serviciosAdicionales; }
    public double getTarifa() { return tarifa; }

    /** @return Cadena multilínea con los detalles del viaje. */
    public String obtenerDetalle() {
        return new StringBuilder()
                .append("ID: ").append(id).append('\n')
                .append("Ruta: ").append(ruta).append('\n')
                .append("Vehículo: ").append(vehiculo).append('\n')
                .append("Conductor: ").append(conductorPrincipal).append('\n')
                .append("Salida: ").append(fechaHoraSalida).append('\n')
                .append("Llegada estimada: ").append(llegadaEstimada).append('\n')
                .append("Capacidad asignada: ").append(capacidadAsignada).append('\n')
                .append("Paradas opcionales: ").append(paradasOpcionales).append('\n')
                .append("Políticas equipaje: ").append(politicasEquipaje).append('\n')
                .append("Servicios: ").append(serviciosAdicionales).append('\n')
                .append("Tarifa: $").append(String.format("%.2f", tarifa))
                .toString();
    }
}
