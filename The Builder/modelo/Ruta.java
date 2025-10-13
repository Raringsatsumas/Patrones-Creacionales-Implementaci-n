package modelo;

/**
 * Entidad de dominio: Ruta de un viaje.
 */
public class Ruta {
    private String id;
    private String origen;
    private String destino;
    private int kilometros;
    private boolean activa;

    public Ruta(String id, String origen, String destino, int kilometros, boolean activa) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.kilometros = kilometros;
        this.activa = activa;
    }

    public String getId() { return id; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public int getKilometros() { return kilometros; }
    public boolean isActiva() { return activa; }

    @Override public String toString() {
        return id + " (" + origen + " → " + destino + ", " + kilometros + " km)";
    }
}
