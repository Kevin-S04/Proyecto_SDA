package utils;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Clase de utilidad para mostrar diferentes tipos de diálogos de mensajes al usuario.
 * Utiliza JOptionPane de Swing para presentar información, errores o advertencias.
 */
public class Mensajes {

    /**
     * Muestra un diálogo de mensaje de información.
     * @param parentComponent El componente padre para el diálogo (usualmente el JFrame actual),
     * o null para centrarlo en la pantalla.
     * @param mensaje El mensaje de texto a mostrar.
     */
    public static void mostrarInformacion(Component parentComponent, String mensaje) {
        JOptionPane.showMessageDialog(parentComponent, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un diálogo de mensaje de error.
     * @param parentComponent El componente padre para el diálogo, o null.
     * @param mensaje El mensaje de texto de error a mostrar.
     */
    public static void mostrarError(Component parentComponent, String mensaje) {
        JOptionPane.showMessageDialog(parentComponent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un diálogo de mensaje de advertencia.
     * @param parentComponent El componente padre para el diálogo, o null.
     * @param mensaje El mensaje de texto de advertencia a mostrar.
     */
    public static void mostrarAdvertencia(Component parentComponent, String mensaje) {
        JOptionPane.showMessageDialog(parentComponent, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}