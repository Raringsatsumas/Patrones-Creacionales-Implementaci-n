package modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Repositorio en memoria para envíos de mensajería (clave: número de guía).
 */
public class RepositorioEnvios {
    private static final Map<String, MensajeriaRegistro> data = new HashMap<>();

    public boolean existe(String guia){ return data.containsKey(guia.toLowerCase()); }
    public MensajeriaRegistro obtener(String guia){ return data.get(guia.toLowerCase()); }

    public void crear(MensajeriaRegistro r){
        String k = r.numeroGuia.toLowerCase();
        if (data.containsKey(k)) throw new IllegalArgumentException("Ya existe la guía: " + r.numeroGuia);
        data.put(k, r);
    }
    public void actualizar(MensajeriaRegistro r){
        String k = r.numeroGuia.toLowerCase();
        if (!data.containsKey(k)) throw new IllegalArgumentException("No existe la guía: " + r.numeroGuia);
        data.put(k, r);
    }
    public void eliminar(String guia){
        String k = guia.toLowerCase();
        if (!data.containsKey(k)) throw new IllegalArgumentException("No existe la guía: " + guia);
        data.remove(k);
    }
}
