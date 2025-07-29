package views;

import javax.swing.*;
import java.awt.*;

/**
 * Clase LoginView que representa la interfaz gráfica de usuario para el inicio de sesión.
 * Permite al usuario ingresar su correo, contraseña y seleccionar un rol para autenticarse.
 * Esta versión construye la interfaz completamente por código, sin un diseñador de formularios.
 */
public class LoginView extends JFrame {
    private JPanel loginPanel;
    private JTextField usuarioText;
    private JPasswordField password;
    private JButton iniciarSesionButton;
    private JButton crearCuentaButton;
    private JLabel recuperarContraseñaLabel;
    private JComboBox<String> rolBox;

    /**
     * Constructor de la clase LoginView.
     * Inicializa y organiza todos los componentes de la interfaz de usuario.
     */
    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Título o Cabecera
        JLabel titleLabel = new JLabel("Distribución de Alimentos Agropecuarios");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        // Campo de Usuario (Correo)
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(new JLabel("Usuario (Correo):"), gbc);

        usuarioText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(usuarioText, gbc);

        // Campo de Contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(new JLabel("Contraseña:"), gbc);

        password = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(password, gbc);

        // Selector de Rol
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(new JLabel("Rol:"), gbc);

        rolBox = new JComboBox<>(new String[]{"Ganadero", "Inventariado", "Transportista", "Admin"});
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(rolBox, gbc);

        // Botón Iniciar Sesión
        iniciarSesionButton = new JButton("Iniciar Sesión");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(iniciarSesionButton, gbc);

        // Botón Crear Cuenta
        crearCuentaButton = new JButton("Crear Cuenta");
        gbc.gridy = 5; // Una fila más abajo
        loginPanel.add(crearCuentaButton, gbc);

        // Etiqueta Recuperar Contraseña
        recuperarContraseñaLabel = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        recuperarContraseñaLabel.setForeground(Color.BLUE.darker()); // Color azul para simular enlace
        recuperarContraseñaLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de mano al pasar por encima
        gbc.gridy = 6; // Una fila más abajo
        loginPanel.add(recuperarContraseñaLabel, gbc);

        // Agrega el panel principal al JFrame
        this.add(loginPanel);
    }

    /**
     * Obtiene el campo de texto para el nombre de usuario (correo).
     * @return El JTextField para el usuario.
     */
    public JTextField getUsuarioText() {
        return usuarioText;
    }

    /**
     * Obtiene el campo de contraseña.
     * @return El JPasswordField para la contraseña.
     */
    public JPasswordField getPassword() {
        return password;
    }

    /**
     * Obtiene el botón de "Iniciar Sesión".
     * @return El JButton para iniciar sesión.
     */
    public JButton getIniciarSesionButton() {
        return iniciarSesionButton;
    }

    /**
     * Obtiene el botón de "Crear Cuenta".
     * @return El JButton para crear una nueva cuenta.
     */
    public JButton getCrearCuentaButton() {
        return crearCuentaButton;
    }

    /**
     * Obtiene la etiqueta (JLabel) para "Recuperar Contraseña".
     * Esta etiqueta actúa como un enlace clicable.
     * @return El JLabel para recuperar contraseña.
     */
    public JLabel getRecuperarContraseñaLabel() {
        return recuperarContraseñaLabel;
    }

    /**
     * Obtiene el JComboBox para seleccionar el rol del usuario.
     * @return El JComboBox que contiene los roles disponibles.
     */
    public JComboBox<String> getRolBox() {
        return rolBox;
    }
}