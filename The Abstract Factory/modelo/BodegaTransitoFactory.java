package modelo;

import java.util.HashMap;
import java.util.Map;

/** Fábrica concreta: Bodega de TRÁNSITO. */
public class BodegaTransitoFactory implements BodegaFactory {

    @Override public InventarioService crearInventario() { return new InventarioTransito(); }
    @Override public OperacionLogistica crearOperacion() { return new OperacionTransito(); }
    public String getNombre() { return "TRANSITO"; }

    private static class InventarioTransito implements InventarioService {
        private static final Map<String, Paquete> DATA = new HashMap<>();

        @Override public String registrarIngreso(Paquete p) { DATA.put(p.getCodigo(), p); return "TRANSITO - ingreso HUB: " + p.getCodigo(); }
        @Override public String registrarSalida(Paquete p)  { DATA.remove(p.getCodigo()); return "TRANSITO - salida a HUB: " + p.getCodigo(); }
        @Override public String actualizarStock()           { return "TRANSITO - stock actualizado. Reg=" + DATA.size(); }

        @Override public String actualizarRegistro(Paquete p) {
            if (!DATA.containsKey(p.getCodigo())) return "TRANSITO - no existe: " + p.getCodigo();
            DATA.put(p.getCodigo(), p);
            return "TRANSITO - actualizado: " + p.getCodigo();
        }
        @Override public String eliminarRegistro(String codigo) {
            return DATA.remove(codigo) != null ? "TRANSITO - eliminado: " + codigo
                                               : "TRANSITO - no existe: " + codigo;
        }
    }

    private static class OperacionTransito implements OperacionLogistica {
        @Override public String asignarUbicacion(Paquete p) { return p.getPesoKg() > 20 ? "CARRIL-PESADO" : "CARRIL-LIGERO"; }
        @Override public boolean validarMovimiento(Paquete p) { return p.getVolumenCm3() <= 150000; }
        @Override public String aplicarMovimiento(Paquete p)  { return "TRANSITO - re-ruteado a HUB-Sec."; }
        @Override public String generarResumen()              { return "TRANSITO - 500 en tránsito, 30 retenidos."; }
    }
}
