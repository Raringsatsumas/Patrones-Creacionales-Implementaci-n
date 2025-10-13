package principal;

import vista.MainView;
import javax.swing.SwingUtilities;

public class Main {
    /**
     * Método principal. Levanta la interfaz gráfica en el hilo de eventos de Swing.
     *
     * @param args argumentos de línea de comandos 
     */
    public static void main(String[] args) {
        // Siempre crear/mostrar UI en el EDT para evitar condiciones de carrera en Swing.
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
