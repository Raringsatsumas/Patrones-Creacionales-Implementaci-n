package modelo;

import java.time.LocalDateTime;

/**
 * Entidad de dominio: Conductor asignado al viaje.
 */
public class Conductor {
    private String id;
    private String nombre;
    private int horasTrabajadasHoy;
    private int horasDescanso;
    private LocalDateTime proximoDescanso;

    public Conductor(String id, String nombre, int horasTrabajadasHoy, int horasDescanso) {
        this.id = id;
        this.nombre = nombre;
        this.horasTrabajadasHoy = horasTrabajadasHoy;
        this.horasDescanso = horasDescanso;
        this.proximoDescanso = LocalDateTime.now().plusHours(Math.max(0, 8 - horasDescanso));
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getHorasTrabajadasHoy() { return horasTrabajadasHoy; }
    public int getHorasDescanso() { return horasDescanso; }
    public LocalDateTime getProximoDescanso() { return proximoDescanso; }

    @Override public String toString() {
        return nombre + " (trab=" + horasTrabajadasHoy + "h, descanso=" + horasDescanso + "h)";
    }
}
