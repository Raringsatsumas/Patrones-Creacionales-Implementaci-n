package modelo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz Builder para construir un {@link ViajeProgramado}.
 * Se añadió {@link #setId(String)} para poder actualizar un viaje reutilizando su ID.
 */
public interface ViajeProgramadoBuilder {

    /** Reinicia el estado interno (nuevo producto). */
    ViajeProgramadoBuilder reset();

    /** Define manualmente el ID del viaje (útil para actualizar). */
    ViajeProgramadoBuilder setId(String id);

    /** Fija la ruta (debe estar activa). */
    ViajeProgramadoBuilder setRuta(Ruta ruta);

    /** Fija horario de salida y calcula llegada estimada. */
    ViajeProgramadoBuilder setHorario(LocalDateTime salida, int duracionHoras);

    /** Asigna un vehículo verificando mantenimiento y capacidad. */
    ViajeProgramadoBuilder asignarVehiculo(Vehiculo vehiculo);

    /** Asigna conductor validando horas y descanso. */
    ViajeProgramadoBuilder asignarConductor(Conductor conductor);

    /** Establece la tarifa final. */
    ViajeProgramadoBuilder setTarifa(double tarifa);

    /** Configura paradas opcionales. */
    ViajeProgramadoBuilder setParadasOpcionales(List<String> paradas);

    /** Configura políticas de equipaje. */
    ViajeProgramadoBuilder setPoliticasEquipaje(String politicas);

    /** Configura servicios adicionales. */
    ViajeProgramadoBuilder setServiciosAdicionales(List<String> servicios);

    /** Termina la construcción y devuelve el viaje listo. */
    ViajeProgramado build();
}
