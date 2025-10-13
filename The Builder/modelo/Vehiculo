package modelo;

/**
 * Vehículo que presta el servicio del viaje.
 */
public class Vehiculo {
    private String id;
    private String tipo;          
    private int capacidad;
    private boolean enMantenimiento;

    public Vehiculo(String id, String tipo, int capacidad, boolean enMantenimiento) {
        this.id = id;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.enMantenimiento = enMantenimiento;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public int getCapacidad() { return capacidad; }
    public boolean isEnMantenimiento() { return enMantenimiento; }

    @Override public String toString() {
        return id + " [" + tipo + ", cap=" + capacidad + (enMantenimiento? ", MTO":"") + "]";
    }
}
