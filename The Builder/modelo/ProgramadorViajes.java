package modelo;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ProgramadorViajes {

    private final ViajeProgramadoBuilder builder;

    public ProgramadorViajes(ViajeProgramadoBuilder builder) {
        this.builder = builder;
    }

    //  Recetas sin ID (creación)
    public ViajeProgramado programarExpress(Ruta r, Vehiculo v, Conductor c,
                                            LocalDateTime s, int h, double t) {
        return builder.reset()
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList()).setServiciosAdicionales(Arrays.asList("WiFi"))
                .setPoliticasEquipaje("1 maleta + 1 de mano").setTarifa(t).build();
    }

    public ViajeProgramado programarEstandar(Ruta r, Vehiculo v, Conductor c,
                                             LocalDateTime s, int h, double t) {
        return builder.reset()
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList("Parador A", "Parador B"))
                .setServiciosAdicionales(Arrays.asList("WiFi", "Snack"))
                .setPoliticasEquipaje("2 maletas + 1 de mano").setTarifa(t).build();
    }

    public ViajeProgramado programarNocturno(Ruta r, Vehiculo v, Conductor c,
                                             LocalDateTime s, int h, double t) {
        return builder.reset()
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList("Parador 24h"))
                .setServiciosAdicionales(Arrays.asList("WiFi", "Cobija"))
                .setPoliticasEquipaje("1 maleta + 1 de mano").setTarifa(t).build();
    }

    public ViajeProgramado programarVip(Ruta r, Vehiculo v, Conductor c,
                                        LocalDateTime s, int h, double t) {
        return builder.reset()
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList())
                .setServiciosAdicionales(Arrays.asList("WiFi", "Snack Premium", "Bebida"))
                .setPoliticasEquipaje("2 maletas + 1 de mano (prioridad)").setTarifa(t).build();
    }

    // ID (actualización)
    public ViajeProgramado programarExpress(String id, Ruta r, Vehiculo v, Conductor c,
                                            LocalDateTime s, int h, double t) {
        return builder.reset().setId(id)
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList()).setServiciosAdicionales(Arrays.asList("WiFi"))
                .setPoliticasEquipaje("1 maleta + 1 de mano").setTarifa(t).build();
    }

    public ViajeProgramado programarEstandar(String id, Ruta r, Vehiculo v, Conductor c,
                                             LocalDateTime s, int h, double t) {
        return builder.reset().setId(id)
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList("Parador A", "Parador B"))
                .setServiciosAdicionales(Arrays.asList("WiFi", "Snack"))
                .setPoliticasEquipaje("2 maletas + 1 de mano").setTarifa(t).build();
    }

    public ViajeProgramado programarNocturno(String id, Ruta r, Vehiculo v, Conductor c,
                                             LocalDateTime s, int h, double t) {
        return builder.reset().setId(id)
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList("Parador 24h"))
                .setServiciosAdicionales(Arrays.asList("WiFi", "Cobija"))
                .setPoliticasEquipaje("1 maleta + 1 de mano").setTarifa(t).build();
    }

    public ViajeProgramado programarVip(String id, Ruta r, Vehiculo v, Conductor c,
                                        LocalDateTime s, int h, double t) {
        return builder.reset().setId(id)
                .setRuta(r).setHorario(s, h).asignarVehiculo(v).asignarConductor(c)
                .setParadasOpcionales(Arrays.asList())
                .setServiciosAdicionales(Arrays.asList("WiFi", "Snack Premium", "Bebida"))
                .setPoliticasEquipaje("2 maletas + 1 de mano (prioridad)").setTarifa(t).build();
    }
}
