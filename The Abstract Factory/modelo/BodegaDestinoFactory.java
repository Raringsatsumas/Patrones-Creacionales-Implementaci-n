package modelo;

import java.util.HashMap;
import java.util.Map;

/** Fábrica concreta: Bodega de DESTINO. */
public class BodegaDestinoFactory implements BodegaFactory {

    @Override public InventarioService crearInventario() { return new InventarioDestino(); }
    @Override public OperacionLogistica crearOperacion() { return new OperacionDestino(); }
    public String getNombre() { return "DESTINO"; }

    private static class InventarioDestino implements InventarioService {
        private static final Map<String, Paquete> DATA = new HashMap<>();

        @Override public String registrarIngreso(Paquete p) { DATA.put(p.getCodigo(), p); return "DESTINO - ingreso CD: " + p.getCodigo(); }
        @Override public String registrarSalida(Paquete p)  { DATA.remove(p.getCodigo()); return "DESTINO - salida última milla: " + p.getCodigo(); }
        @Override public String actualizarStock()           { return "DESTINO - stock actualizado por rutas. Reg=" + DATA.size(); }

        @Override public String actualizarRegistro(Paquete p) {
            if (!DATA.containsKey(p.getCodigo())) return "DESTINO - no existe: " + p.getCodigo();
            DATA.put(p.getCodigo(), p);
            return "DESTINO - actualizado: " + p.getCodigo();
        }
        @Override public String eliminarRegistro(String codigo) {
            return DATA.remove(codigo) != null ? "DESTINO - eliminado: " + codigo
                                               : "DESTINO - no existe: " + codigo;
        }
    }

    private static class OperacionDestino implements OperacionLogistica {
        @Override public String asignarUbicacion(Paquete p) { return "ZONA-" + (p.getCodigo().hashCode() & 7); }
        @Override public boolean validarMovimiento(Paquete p) { return p.getPesoKg() <= 50; }
        @Override public String aplicarMovimiento(Paquete p)  { return "DESTINO - asignado a reparto local."; }
        @Override public String generarResumen()              { return "DESTINO - 200 en cross-dock, 80 en ruta."; }
    }
}
