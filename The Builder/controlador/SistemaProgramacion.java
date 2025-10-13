package controlador;

import modelo.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Controlador: CRUD de viajes + orquestación con Director/Builder.
 */
public class SistemaProgramacion {

    /** Repositorio en memoria por ID. */
    private final Map<String, ViajeProgramado> viajes = new LinkedHashMap<>();
    private final ProgramadorViajes director = new ProgramadorViajes(new ViajeProgramadoConcreteBuilder());

    /** Crea un viaje nuevo y lo guarda. */
    public ViajeProgramado crearProgramacion(String tipo, Ruta ruta, Vehiculo v, Conductor c,
                                             LocalDateTime salida, int duracionHoras, double tarifa) {
        ViajeProgramado vp = switch (tipo.toUpperCase()) {
            case "EXPRESS"  -> director.programarExpress(ruta, v, c, salida, duracionHoras, tarifa);
            case "ESTANDAR" -> director.programarEstandar(ruta, v, c, salida, duracionHoras, tarifa);
            case "NOCTURNO" -> director.programarNocturno(ruta, v, c, salida, duracionHoras, tarifa);
            case "VIP"      -> director.programarVip(ruta, v, c, salida, duracionHoras, tarifa);
            default -> throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        };
        viajes.put(vp.getId(), vp);
        return vp;
    }

    /** Actualiza un viaje existente conservando su ID. */
    public ViajeProgramado actualizarProgramacion(String id, String tipo, Ruta ruta, Vehiculo v, Conductor c,
                                                  LocalDateTime salida, int duracionHoras, double tarifa) {
        if (!viajes.containsKey(id)) throw new IllegalArgumentException("No existe el viaje con id: " + id);
        ViajeProgramado vp = switch (tipo.toUpperCase()) {
            case "EXPRESS"  -> director.programarExpress(id, ruta, v, c, salida, duracionHoras, tarifa);
            case "ESTANDAR" -> director.programarEstandar(id, ruta, v, c, salida, duracionHoras, tarifa);
            case "NOCTURNO" -> director.programarNocturno(id, ruta, v, c, salida, duracionHoras, tarifa);
            case "VIP"      -> director.programarVip(id, ruta, v, c, salida, duracionHoras, tarifa);
            default -> throw new IllegalArgumentException("Tipo no soportado: " + tipo);
        };
        viajes.put(id, vp);
        return vp;
    }

    /** Elimina un viaje por ID. */
    public boolean eliminarProgramacion(String id) {
        return viajes.remove(id) != null;
    }

    /** Listado de viajes. */
    public List<ViajeProgramado> listarViajes() { return List.copyOf(viajes.values()); }
}
