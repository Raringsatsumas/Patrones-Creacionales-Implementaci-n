package vista;

import controlador.SistemaReservas;
import modelo.DatosReserva;
import modelo.Dimensiones;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.*;

/**
 * Vista principal del sistema.
 * - Muestra/oculta secciones según el tipo: PASAJERO, MENSAJERIA, AMBOS.
 * - Ejecuta validaciones de entrada (incluye nombre solo letras).
 * - Expone botones CRUD para cada sección, con repositorios en memoria.
 * - Emite tiquetes y decide estado de pago automáticamente:
 *   si todas las validaciones pasan → APROBADO; si algo falla → RECHAZADO.
 */
public class MainView extends JFrame {
    private final SistemaReservas controller = new SistemaReservas();

    // Tipo de servicio (control UI principal)
    private final JComboBox<String> cbTipo = new JComboBox<>(new String[]{"PASAJERO", "MENSAJERIA", "AMBOS"});
    private final JLabel lblEstadoPago = new JLabel("Estado de pago: (pendiente)");

    // Paneles con sus layouts: podremos mostrarlos/ocultarlos
    private final JPanel panelPasajero = new JPanel(new GridBagLayout());
    private final JPanel panelMensajeria = new JPanel(new GridBagLayout());

    //  Campos PASAJERO 
    private final JTextField txtNombre = new JTextField(16);
    private final JTextField txtAsiento = new JTextField(16);
    private final JTextField txtRuta = new JTextField(16);
    private final JTextField txtFecha = new JTextField(LocalDate.now().toString(), 16);

    //  Campos MENSAJERIA 
    private final JTextField txtGuia  = new JTextField(16);
    private final JTextField txtPeso  = new JTextField(16);
    private final JTextField txtLargo = new JTextField(16);
    private final JTextField txtAncho = new JTextField(16);
    private final JTextField txtAlto  = new JTextField(16);

    // Salida de los tiquetes generados
    private final JTextArea txtSalida = new JTextArea(12, 40);

    /**
     * Construye la vista, inicializa controles y listeners.
     */
    public MainView() {
        super("Emisión de Tiquete Electrónico - Factory (MVC)");
        construirUI();
    }

    /**
     * Construye la UI: cabecera, paneles por sección y botón de emisión.
     */
    private void construirUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0;

        // Cabecera: tipo servicio + estado de pago
        root.add(new JLabel("Tipo servicio:"), c);
        c.gridx = 1; c.weightx = 1.0; root.add(cbTipo, c); c.weightx = 0.0;

        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        root.add(lblEstadoPago, c); c.gridwidth = 1;

        //  Panel PASAJERO 
        construirPanelPasajero();
        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        root.add(panelPasajero, c); c.gridwidth = 1;

        //  Panel MENSAJERIA 
        construirPanelMensajeria();
        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        root.add(panelMensajeria, c); c.gridwidth = 1;

        //  Botón general de emisión 
        JButton btnEmitir = new JButton("Emitir tiquete(s)");
        btnEmitir.addActionListener(e -> emitir());

        JPanel south = new JPanel(new BorderLayout());
        txtSalida.setEditable(false);
        south.add(new JScrollPane(txtSalida), BorderLayout.CENTER);
        south.add(btnEmitir, BorderLayout.SOUTH);

        add(root, BorderLayout.NORTH);
        add(south, BorderLayout.CENTER);

        // Cambio de visibilidad al seleccionar tipo
        cbTipo.addActionListener(e -> actualizarVisibilidad());

        pack();
        setLocationRelativeTo(null);
        actualizarVisibilidad();
    }

    //  ayudantes de layout

    /**
     * Añade una fila (etiqueta + campo)
     */
    private void addRow(JPanel p, GridBagConstraints c, String label, JComponent field){
        c.gridx=0; c.weightx=0; c.fill=GridBagConstraints.NONE; p.add(new JLabel(label), c);
        c.gridx=1; c.weightx=1; c.fill=GridBagConstraints.HORIZONTAL; p.add(field, c);
    }

    /**
     * Crea una pequeña botonera horizontal para CRUD.
     */
    private JPanel botonera(String nombreCrear, Runnable onCrear, String nombreAct, Runnable onAct, String nombreDel, Runnable onDel){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        JButton b1 = new JButton(nombreCrear); b1.addActionListener(e -> onCrear.run());
        JButton b2 = new JButton(nombreAct);   b2.addActionListener(e -> onAct.run());
        JButton b3 = new JButton(nombreDel);   b3.addActionListener(e -> onDel.run());
        panel.add(b1); panel.add(b2); panel.add(b3);
        return panel;
    }

    // Panel PASAJERO

    /**
     * Construye el panel de Pasajero: campos + botonera CRUD.
     */
    private void construirPanelPasajero() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4); c.gridy=0;

        c.gridx=0; c.gridwidth=2; c.weightx=1; c.fill=GridBagConstraints.HORIZONTAL;
        panelPasajero.add(new JLabel("---- PASAJERO ----"), c);
        c.gridwidth=1;

        c.gridy++; addRow(panelPasajero, c, "Nombre pasajero:", txtNombre);
        c.gridy++; addRow(panelPasajero, c, "Número de asiento:", txtAsiento);
        c.gridy++; addRow(panelPasajero, c, "Ruta:", txtRuta);
        c.gridy++; addRow(panelPasajero, c, "Fecha (yyyy-MM-dd):", txtFecha);

        // Botonera CRUD pasajero
        c.gridy++; c.gridx=0; c.gridwidth=2;
        panelPasajero.add(
            botonera("Crear", this::crearPasajero, "Actualizar", this::actualizarPasajero, "Eliminar", this::eliminarPasajero),
            c
        );
        c.gridwidth=1;
    }

    // Panel MENSAJERIA 

    /**
     * Construye el panel de Mensajería: campos + botonera CRUD.
     */
    private void construirPanelMensajeria() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4); c.gridy=0;

        c.gridx=0; c.gridwidth=2; c.weightx=1; c.fill=GridBagConstraints.HORIZONTAL;
        panelMensajeria.add(new JLabel("---- MENSAJERIA ----"), c);
        c.gridwidth=1;

        c.gridy++; addRow(panelMensajeria, c, "Número de guía:", txtGuia);
        c.gridy++; addRow(panelMensajeria, c, "Peso (kg):", txtPeso);
        c.gridy++; addRow(panelMensajeria, c, "Largo (cm):", txtLargo);
        c.gridy++; addRow(panelMensajeria, c, "Ancho (cm):", txtAncho);
        c.gridy++; addRow(panelMensajeria, c, "Alto (cm):", txtAlto);

        // Botonera CRUD mensajería
        c.gridy++; c.gridx=0; c.gridwidth=2;
        panelMensajeria.add(
            botonera("Crear", this::crearEnvio, "Actualizar", this::actualizarEnvio, "Eliminar", this::eliminarEnvio),
            c
        );
        c.gridwidth=1;
    }

    // UI: visibilidad por tipo 

    /**
     * Muestra/oculta paneles según el tipo seleccionado en el combo.
     */
    private void actualizarVisibilidad() {
        String tipo = cbTipo.getSelectedItem().toString();
        boolean verPax = tipo.equalsIgnoreCase("PASAJERO") || tipo.equalsIgnoreCase("AMBOS");
        boolean verMen = tipo.equalsIgnoreCase("MENSAJERIA") || tipo.equalsIgnoreCase("AMBOS");
        panelPasajero.setVisible(verPax);
        panelMensajeria.setVisible(verMen);
        lblEstadoPago.setText("Estado de pago: (pendiente)");
        revalidate(); repaint();
    }

    // CRUD handlers: Pasajero 

    /** Crear pasajero en repositorio (valida nombre solo letras). */
    private void crearPasajero(){
        try{
            String nombre = nombreSoloLetras(txtNombre.getText().trim());
            int asiento = parseEntero(txtAsiento.getText().trim(), "número de asiento");
            String ruta = txtRuta.getText().trim();
            LocalDate fecha = parseFecha(txtFecha.getText().trim());

            controller.crearPasajero(nombre, asiento, ruta, fecha);
            JOptionPane.showMessageDialog(this, "Pasajero creado correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error crear pasajero", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Actualiza un pasajero existente. */
    private void actualizarPasajero(){
        try{
            String nombre = nombreSoloLetras(txtNombre.getText().trim());
            int asiento = parseEntero(txtAsiento.getText().trim(), "número de asiento");
            String ruta = txtRuta.getText().trim();
            LocalDate fecha = parseFecha(txtFecha.getText().trim());

            controller.actualizarPasajero(nombre, asiento, ruta, fecha);
            JOptionPane.showMessageDialog(this, "Pasajero actualizado.", "OK", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error actualizar pasajero", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Elimina un pasajero por nombre. */
    private void eliminarPasajero(){
        try{
            String nombre = txtNombre.getText().trim();
            controller.eliminarPasajero(nombre);
            JOptionPane.showMessageDialog(this, "Pasajero eliminado.", "OK", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error eliminar pasajero", JOptionPane.WARNING_MESSAGE);
        }
    }

    //  CRUD handlers: Envíos 

    /** Crear envío (mensajería) en repositorio. */
    private void crearEnvio(){
        try{
            String guia = txtGuia.getText().trim();
            double peso = parseDouble(txtPeso.getText().trim(), "peso");
            double L = parseDouble(txtLargo.getText().trim(), "largo");
            double A = parseDouble(txtAncho.getText().trim(), "ancho");
            double H = parseDouble(txtAlto.getText().trim(), "alto");
            controller.crearEnvio(guia, peso, new Dimensiones(L,A,H));
            JOptionPane.showMessageDialog(this, "Envío creado correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error crear envío", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Actualiza un envío existente. */
    private void actualizarEnvio(){
        try{
            String guia = txtGuia.getText().trim();
            double peso = parseDouble(txtPeso.getText().trim(), "peso");
            double L = parseDouble(txtLargo.getText().trim(), "largo");
            double A = parseDouble(txtAncho.getText().trim(), "ancho");
            double H = parseDouble(txtAlto.getText().trim(), "alto");
            controller.actualizarEnvio(guia, peso, new Dimensiones(L,A,H));
            JOptionPane.showMessageDialog(this, "Envío actualizado.", "OK", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error actualizar envío", JOptionPane.WARNING_MESSAGE);
        }
    }

    /** Elimina un envío por número de guía. */
    private void eliminarEnvio(){
        try{
            String guia = txtGuia.getText().trim();
            controller.eliminarEnvio(guia);
            JOptionPane.showMessageDialog(this, "Envío eliminado.", "OK", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error eliminar envío", JOptionPane.WARNING_MESSAGE);
        }
    }

    //  Emisión con pago automático 

    /**
     * Ejecuta la emisión de tiquete(s) según el tipo seleccionado:
     * - Si todas las validaciones/operaciones internas pasan → APROBADO.
     * - Ante cualquier excepción/validación fallida → RECHAZADO.
     */
    private void emitir() {
        txtSalida.setText("");
        String tipo = cbTipo.getSelectedItem().toString();
        StringBuilder resumen = new StringBuilder();
        boolean aprobado = true;

        // Pasajero
        try {
            if (tipo.equalsIgnoreCase("PASAJERO") || tipo.equalsIgnoreCase("AMBOS")) {
                String nombre = nombreSoloLetras(txtNombre.getText().trim());
                DatosReserva pax = new DatosReserva();
                pax.setTipoServicio("PASAJERO");
                pax.setPasajeroNombre(nombre);
                pax.setRuta(txtRuta.getText().trim());
                pax.setNumeroAsiento(parseEntero(txtAsiento.getText().trim(), "número de asiento"));
                pax.setFechaViaje(parseFecha(txtFecha.getText().trim()));
                resumen.append(controller.procesarReserva(pax)).append("\n\n");
            }
        } catch (Exception ex) {
            aprobado = false;
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación (Pasajero)", JOptionPane.WARNING_MESSAGE);
        }

        // Mensajería
        try {
            if (tipo.equalsIgnoreCase("MENSAJERIA") || tipo.equalsIgnoreCase("AMBOS")) {
                DatosReserva men = new DatosReserva();
                men.setTipoServicio("MENSAJERIA");
                men.setNumeroGuia(txtGuia.getText().trim());
                men.setPesoKg(parseDouble(txtPeso.getText().trim(), "peso"));
                double L = parseDouble(txtLargo.getText().trim(), "largo");
                double A = parseDouble(txtAncho.getText().trim(), "ancho");
                double H = parseDouble(txtAlto.getText().trim(), "alto");
                men.setDimensiones(new Dimensiones(L, A, H));
                resumen.append(controller.procesarReserva(men)).append("\n\n");
            }
        } catch (Exception ex) {
            aprobado = false;
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación (Mensajería)", JOptionPane.WARNING_MESSAGE);
        }

        // Resultado “pago”
        lblEstadoPago.setText("Estado de pago: " + (aprobado ? "APROBADO" : "RECHAZADO"));
        if (aprobado) txtSalida.setText(resumen.toString().trim());
    }

    // Parsers & validaciones de entrada 

    /** Valida nombre con controlador (solo letras y espacios). */
    private String nombreSoloLetras(String s){
        controller.validarNombreSoloLetras(s);
        return s.trim();
    }

    /** Convierte a entero con mensaje de error amigable. */
    private int parseEntero(String s, String nombreCampo){
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e){ throw new IllegalArgumentException("El campo '"+nombreCampo+"' debe ser un entero."); }
    }

    /** Convierte a double con mensaje de error amigable. */
    private double parseDouble(String s, String nombreCampo){
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e){ throw new IllegalArgumentException("El campo '"+nombreCampo+"' debe ser numérico."); }
    }

    /** Parsea fecha en formato yyyy-MM-dd. */
    private LocalDate parseFecha(String s){
        try { return LocalDate.parse(s); }
        catch (DateTimeParseException e){ throw new IllegalArgumentException("Fecha inválida. Formato yyyy-MM-dd."); }
    }
}
