package modelo;

import java.util.HashMap;
import java.util.Map;

/** Fábrica concreta: Bodega de ORIGEN. */
public class BodegaOrigenFactory implements BodegaFactory {

    @Override public InventarioService crearInventario() { return new InventarioOrigen(); }
    @Override public OperacionLogistica crearOperacion() { return new OperacionOrigen(); }
    public String getNombre() { return "ORIGEN"; }

    /** Inventario específico de ORIGEN (repositorio en memoria por código). */
    private static class InventarioOrigen implements InventarioService {
        private static final Map<String, Paquete> DATA = new HashMap<>();

        @Override public String registrarIngreso(Paquete p) {
            DATA.put(p.getCodigo(), p);
            return "ORIGEN - ingreso: " + p.getCodigo();
        }
        @Override public String registrarSalida(Paquete p)  {
            DATA.remove(p.getCodigo());
            return "ORIGEN - salida: " + p.getCodigo();
        }
        @Override public String actualizarStock()           { return "ORIGEN - stock actualizado (staging). Reg=" + DATA.size(); }

        @Override public String actualizarRegistro(Paquete p) {
            if (!DATA.containsKey(p.getCodigo())) return "ORIGEN - no existe: " + p.getCodigo();
            DATA.put(p.getCodigo(), p);
            return "ORIGEN - actualizado: " + p.getCodigo();
        }
        @Override public String eliminarRegistro(String codigo) {
            return DATA.remove(codigo) != null ? "ORIGEN - eliminado: " + codigo
                                               : "ORIGEN - no existe: " + codigo;
        }
    }

    /** Operación logística específica de ORIGEN. */
    private static class OperacionOrigen implements OperacionLogistica {
        @Override public String asignarUbicacion(Paquete p) { return p.getPesoKg() > 30 ? "STG-PESADO" : "STG-LIGERO"; }
        @Override public boolean validarMovimiento(Paquete p) { return p.getPesoKg() <= 70; }
        @Override public String aplicarMovimiento(Paquete p)  { return "ORIGEN - a ANDEN-CARGA"; }
        @Override public String generarResumen()              { return "ORIGEN - 100 en staging, 20 listos."; }
    }
}
