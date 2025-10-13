package principal;

import javax.swing.SwingUtilities;
import vista.MainView;

/**
 * Punto de entrada de la aplicación.
 * Lanza la UI en el Event Dispatch Thread de Swing.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
