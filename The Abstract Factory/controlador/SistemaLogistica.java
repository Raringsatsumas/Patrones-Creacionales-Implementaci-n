package controlador;

import modelo.*;

/**
 * Controlador MVC: expone operaciones de negocio y CRUD contra el InventarioService.
 */
public class SistemaLogistica {

    private BodegaFactory getFactory(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "ORIGEN"   -> new BodegaOrigenFactory();
            case "TRANSITO" -> new BodegaTransitoFactory();
            case "DESTINO"  -> new BodegaDestinoFactory();
            default -> throw new IllegalArgumentException("Tipo de bodega no soportado: " + tipo);
        };
    }

    //  flujo estándar 
    public String procesarMovimiento(Paquete p, String tipoBodega) {
        BodegaFactory f = getFactory(tipoBodega);
        InventarioService inv = f.crearInventario();
        OperacionLogistica op = f.crearOperacion();

        StringBuilder sb = new StringBuilder("== ").append(tipoBodega).append(" ==\n");
        sb.append(inv.registrarIngreso(p)).append('\n');
        sb.append("Ubicación: ").append(op.asignarUbicacion(p)).append('\n');

        if (op.validarMovimiento(p)) {
            sb.append(op.aplicarMovimiento(p)).append('\n');
            sb.append(inv.actualizarStock()).append('\n');
        } else {
            sb.append("Movimiento rechazado\n");
            sb.append(inv.registrarSalida(p)).append('\n');
        }
        sb.append(op.generarResumen());
        return sb.toString();
    }

    //operaciones CRUD
    public String actualizarRegistro(Paquete p, String tipoBodega) {
        InventarioService inv = getFactory(tipoBodega).crearInventario();
        return inv.actualizarRegistro(p);
    }

    public String eliminarRegistro(String codigo, String tipoBodega) {
        InventarioService inv = getFactory(tipoBodega).crearInventario();
        return inv.eliminarRegistro(codigo);
    }

    public String consultarInventario(String tipoBodega) {
        OperacionLogistica op = getFactory(tipoBodega).crearOperacion();
        return "== Inventario " + tipoBodega + " ==\n" + op.generarResumen();
    }
}
