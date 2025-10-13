package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Builder concreto de ViajeProgramado.
 */
public class ViajeProgramadoConcreteBuilder implements ViajeProgramadoBuilder {

    private ViajeProgramado viaje;

    /** Crea el builder y deja un viaje nuevo con ID generado. */
    public ViajeProgramadoConcreteBuilder() { reset(); }

    /** Reinicia el builder con un viaje nuevo y un ID aleatorio (prefijo VIA-). */
    @Override
    public ViajeProgramadoBuilder reset() {
        viaje = new ViajeProgramado();
        viaje.setId("VIA-" + UUID.randomUUID().toString().substring(0, 8));
        return this;
    }

    /** Fija el ID manualmente (útil para actualizar). Ignora nulos/vacíos. */
    @Override
    public ViajeProgramadoBuilder setId(String id) {
        if (id != null && !id.isBlank()) viaje.setId(id);
        return this;
    }

    /** Asigna la ruta (debe estar activa). */
    @Override
    public ViajeProgramadoBuilder setRuta(Ruta ruta) {
        if (ruta == null || !ruta.isActiva()) throw new IllegalArgumentException("Ruta inválida o inactiva.");
        viaje.setRuta(ruta);
        return this;
    }

    /** Define salida y calcula llegada estimada (duración en horas > 0). */
    @Override
    public ViajeProgramadoBuilder setHorario(LocalDateTime salida, int duracionHoras) {
        if (salida == null || duracionHoras <= 0) throw new IllegalArgumentException("Horario inválido.");
        viaje.setFechaHoraSalida(salida);
        viaje.setLlegadaEstimada(salida.plusHours(duracionHoras));
        return this;
    }

    /** Asigna vehículo (no en mantenimiento, capacidad > 0) y fija capacidad 95% por defecto. */
    @Override
    public ViajeProgramadoBuilder asignarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null || vehiculo.isEnMantenimiento())
            throw new IllegalArgumentException("Vehículo inválido o en mantenimiento.");
        if (vehiculo.getCapacidad() <= 0) throw new IllegalArgumentException("Vehículo sin capacidad.");
        viaje.setVehiculo(vehiculo);
        viaje.setCapacidadAsignada(Math.max(1, (int) Math.floor(vehiculo.getCapacidad() * 0.95)));
        return this;
    }

    /** Asigna conductor (≤10h trabajadas y ≥8h de descanso). */
    @Override
    public ViajeProgramadoBuilder asignarConductor(Conductor conductor) {
        if (conductor == null) throw new IllegalArgumentException("Conductor inválido.");
        if (conductor.getHorasTrabajadasHoy() > 10) throw new IllegalArgumentException("Excede 10h trabajadas.");
        if (conductor.getHorasDescanso() < 8) throw new IllegalArgumentException("Requiere ≥8h de descanso.");
        viaje.setConductorPrincipal(conductor);
        return this;
    }

    /** Define la tarifa (> 0). */
    @Override
    public ViajeProgramadoBuilder setTarifa(double tarifa) {
        if (tarifa <= 0) throw new IllegalArgumentException("Tarifa inválida.");
        viaje.setTarifa(tarifa);
        return this;
    }

    /** Configura paradas opcionales (si es null, usa lista vacía). */
    @Override
    public ViajeProgramadoBuilder setParadasOpcionales(List<String> paradas) {
        viaje.setParadasOpcionales(paradas == null ? new ArrayList<>() : paradas);
        return this;
    }

    /** Configura políticas de equipaje (si es null, usa “Política estándar”). */
    @Override
    public ViajeProgramadoBuilder setPoliticasEquipaje(String politicas) {
        viaje.setPoliticasEquipaje(politicas == null ? "Política estándar" : politicas);
        return this;
    }

    /** Configura servicios adicionales (si es null, usa lista vacía). */
    @Override
    public ViajeProgramadoBuilder setServiciosAdicionales(List<String> servicios) {
        viaje.setServiciosAdicionales(servicios == null ? new ArrayList<>() : servicios);
        return this;
    }

    /** Valida datos mínimos y devuelve el viaje construido. */
    @Override
    public ViajeProgramado build() {
        if (viaje.getRuta() == null)               throw new IllegalStateException("Falta ruta.");
        if (viaje.getVehiculo() == null)           throw new IllegalStateException("Falta vehículo.");
        if (viaje.getConductorPrincipal() == null) throw new IllegalStateException("Falta conductor.");
        if (viaje.getFechaHoraSalida() == null)    throw new IllegalStateException("Falta horario.");
        if (viaje.getTarifa() <= 0)                throw new IllegalStateException("Falta tarifa.");
        return viaje;
    }
}
