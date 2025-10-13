package vista;

import controlador.SistemaProgramacion;
import modelo.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Vista Swing para programar, actualizar y eliminar viajes (Builder + MVC).
 * El tipo de vehículo es SIEMPRE "BUS" (campo deshabilitado).
 */
public class MainView extends JFrame {

    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"EXPRESS", "ESTANDAR", "NOCTURNO", "VIP"});

    // --- ID para actualizar/eliminar ---
    private final JTextField txtIdViaje = new JTextField(10);

    // Ruta
    private final JTextField txtRutaId = new JTextField(8);
    private final JTextField txtOrigen = new JTextField(10);
    private final JTextField txtDestino = new JTextField(10);
    private final JTextField txtKm = new JTextField(6);
    private final JCheckBox chkActiva = new JCheckBox("Activa", true);

    // Vehículo (tipo fijo BUS)
    private final JTextField txtVehId = new JTextField(8);
    private final JTextField txtVehTipo = new JTextField("BUS", 8); // fijo BUS
    private final JTextField txtCapacidad = new JTextField(6);
    private final JCheckBox chkMto = new JCheckBox("En mantenimiento");

    // Conductor
    private final JTextField txtCondId = new JTextField(8);
    private final JTextField txtCondNombre = new JTextField(12);
    private final JTextField txtHrsTrab = new JTextField(4);
    private final JTextField txtHrsDescanso = new JTextField(4);

    // Horario y tarifa
    private final JTextField txtSalida = new JTextField(16);
    private final JTextField txtDurHoras = new JTextField(4);
    private final JTextField txtTarifa = new JTextField(6);

    private final JTextArea txtOut = new JTextArea(18, 72);

    private final SistemaProgramacion controller = new SistemaProgramacion();

    public MainView() {
        super("Programación de Viajes - Builder (MVC+CRUD)");
        buildUI();
    }

    private void buildUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        txtVehTipo.setEditable(false); // Siempre BUS

        JPanel root = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets=new Insets(5,5,5,5); c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0; c.gridy=0;

        root.add(new JLabel("Tipo de viaje:"), c); c.gridx=1; c.weightx=1; root.add(cbTipo, c); c.weightx=0;

        // ID viaje para actualizar/eliminar
        c.gridx=0; c.gridy++;
        root.add(new JLabel("ID viaje (actualizar/eliminar):"), c);
        c.gridx=1; root.add(txtIdViaje, c);

        // Ruta
        c.gridx=0; c.gridy++; root.add(new JLabel("Ruta ID:"), c); c.gridx=1; root.add(txtRutaId, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Origen:"), c);  c.gridx=1; root.add(txtOrigen, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Destino:"), c); c.gridx=1; root.add(txtDestino, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Kilómetros:"), c); c.gridx=1; root.add(txtKm, c);
        c.gridx=1; c.gridy++; root.add(chkActiva, c);

        // Vehículo
        c.gridx=0; c.gridy++; root.add(new JLabel("Vehículo ID:"), c); c.gridx=1; root.add(txtVehId, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Tipo:"), c);       c.gridx=1; root.add(txtVehTipo, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Capacidad:"), c);  c.gridx=1; root.add(txtCapacidad, c);
        c.gridx=1; c.gridy++; root.add(chkMto, c);

        // Conductor
        c.gridx=0; c.gridy++; root.add(new JLabel("Conductor ID:"), c); c.gridx=1; root.add(txtCondId, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Nombre:"), c);      c.gridx=1; root.add(txtCondNombre, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Horas trabajadas:"), c); c.gridx=1; root.add(txtHrsTrab, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Horas descanso:"), c);   c.gridx=1; root.add(txtHrsDescanso, c);

        // Horario y tarifa
        c.gridx=0; c.gridy++; root.add(new JLabel("Salida (yyyy-MM-ddTHH:mm):"), c); c.gridx=1; root.add(txtSalida, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Duración (horas):"), c);         c.gridx=1; root.add(txtDurHoras, c);
        c.gridx=0; c.gridy++; root.add(new JLabel("Tarifa ($):"), c);               c.gridx=1; root.add(txtTarifa, c);

        // Botones
        JButton btnCrear = new JButton("Crear viaje");
        JButton btnActualizar = new JButton("Actualizar viaje");
        JButton btnEliminar = new JButton("Eliminar viaje");
        JButton btnListar = new JButton("Listar viajes");
        JButton btnLimpiar = new JButton("Limpiar");

        btnCrear.addActionListener(e -> crear());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> listar());
        btnLimpiar.addActionListener(e -> txtOut.setText(""));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.add(btnCrear); actions.add(btnActualizar); actions.add(btnEliminar);
        actions.add(btnListar); actions.add(btnLimpiar);

        txtOut.setEditable(false);
        txtOut.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        add(root, BorderLayout.NORTH);
        add(new JScrollPane(txtOut), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Defaults
        txtRutaId.setText("R-01");
        txtOrigen.setText("Ciudad A");
        txtDestino.setText("Ciudad B");
        txtKm.setText("320");

        txtVehId.setText("BUS-100");
        txtCapacidad.setText("45");

        txtCondId.setText("C-01");
        txtCondNombre.setText("Juan Pérez");
        txtHrsTrab.setText("6");
        txtHrsDescanso.setText("10");

        txtSalida.setText(LocalDateTime.now().plusDays(1).withMinute(0).withSecond(0).withNano(0).toString());
        txtDurHoras.setText("6");
        txtTarifa.setText("35");

        pack(); setLocationRelativeTo(null);
    }

    // ================== acciones ==================
    private void crear() {
        try {
            String tipo = cbTipo.getSelectedItem().toString();
            Ruta r = buildRuta();
            Vehiculo v = buildVehiculo();
            Conductor c = buildConductor();
            LocalDateTime s = parseDateTime(txtSalida.getText().trim());
            int h = parseInt(txtDurHoras.getText().trim(), "duración");
            double t = parseDouble(txtTarifa.getText().trim(), "tarifa");

            ViajeProgramado vp = controller.crearProgramacion(tipo, r, v, c, s, h, t);
            txtIdViaje.setText(vp.getId()); // mostramos el ID resultante
            txtOut.setText("Creado:\n\n" + vp.obtenerDetalle());
        } catch (Exception ex) {
            showErr(ex.getMessage());
        }
    }

    private void actualizar() {
        try {
            String id = txtIdViaje.getText().trim();
            if (id.isEmpty()) throw new IllegalArgumentException("Debe indicar el ID del viaje a actualizar.");
            String tipo = cbTipo.getSelectedItem().toString();
            Ruta r = buildRuta();
            Vehiculo v = buildVehiculo();
            Conductor c = buildConductor();
            LocalDateTime s = parseDateTime(txtSalida.getText().trim());
            int h = parseInt(txtDurHoras.getText().trim(), "duración");
            double t = parseDouble(txtTarifa.getText().trim(), "tarifa");

            ViajeProgramado vp = controller.actualizarProgramacion(id, tipo, r, v, c, s, h, t);
            txtOut.setText("Actualizado:\n\n" + vp.obtenerDetalle());
        } catch (Exception ex) {
            showErr(ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            String id = txtIdViaje.getText().trim();
            if (id.isEmpty()) throw new IllegalArgumentException("Debe indicar el ID del viaje a eliminar.");
            boolean ok = controller.eliminarProgramacion(id);
            txtOut.setText(ok ? "Eliminado el viaje: " + id : "No existe el viaje: " + id);
        } catch (Exception ex) {
            showErr(ex.getMessage());
        }
    }

    private void listar() {
        StringBuilder sb = new StringBuilder("== Viajes programados ==\n\n");
        controller.listarViajes().forEach(v -> sb.append(v.obtenerDetalle()).append("\n------------------------------\n"));
        txtOut.setText(sb.toString());
    }

    // ================== helpers ==================
    private Ruta buildRuta() {
        return new Ruta(
                txtRutaId.getText().trim(),
                txtOrigen.getText().trim(),
                txtDestino.getText().trim(),
                parseInt(txtKm.getText().trim(), "kilómetros"),
                chkActiva.isSelected()
        );
    }

    private Vehiculo buildVehiculo() {
        // Tipo siempre BUS (campo bloqueado)
        return new Vehiculo(
                txtVehId.getText().trim(),
                "BUS",
                parseInt(txtCapacidad.getText().trim(), "capacidad"),
                chkMto.isSelected()
        );
        // Nota: si quisieras reforzar aún más, podrías ignorar cualquier texto del campo y escribir "BUS".
    }

    private Conductor buildConductor() {
        return new Conductor(
                txtCondId.getText().trim(),
                txtCondNombre.getText().trim(),
                parseInt(txtHrsTrab.getText().trim(), "horas trabajadas"),
                parseInt(txtHrsDescanso.getText().trim(), "horas descanso")
        );
    }

    private int parseInt(String s, String campo) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("El campo '"+campo+"' debe ser entero."); }
    }

    private double parseDouble(String s, String campo) {
        try { double d = Double.parseDouble(s); if (d<=0) throw new NumberFormatException(); return d; }
        catch (NumberFormatException e) { throw new IllegalArgumentException("El campo '"+campo+"' debe ser numérico > 0."); }
    }

    private LocalDateTime parseDateTime(String s) {
        try { return LocalDateTime.parse(s); }
        catch (DateTimeParseException e) { throw new IllegalArgumentException("Fecha/hora inválida. Formato: yyyy-MM-ddTHH:mm"); }
    }

    private void showErr(String msg){
        JOptionPane.showMessageDialog(this, msg, "Validación", JOptionPane.WARNING_MESSAGE);
    }
}
