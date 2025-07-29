package views;

import javax.swing.*;
import java.awt.*;

/**
 * Clase LoginView que representa la interfaz gráfica de usuario para el inicio de sesión.
 * Permite al usuario ingresar su correo, contraseña y seleccionar un rol para autenticarse.
 * Esta versión construye la interfaz completamente por código, utilizando un diseño simple con GridLayout.
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
     * Inicializa y organiza todos los componentes de la interfaz de usuario con un diseño simple.
     */
    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350); // Tamaño ajustado para un diseño más simple
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(8, 1, 10, 10)); // 8 filas, 1 columna, 10px de espacio horizontal y vertical

        // Título
        JLabel titleLabel = new JLabel("Bienvenido", SwingConstants.CENTER); // Centrar texto
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginPanel.add(titleLabel);

        // Campo de Usuario (Correo)
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel para centrar el campo
        userPanel.add(new JLabel("Usuario (Correo):"));
        usuarioText = new JTextField(15); // Tamaño más pequeño
        userPanel.add(usuarioText);
        loginPanel.add(userPanel);

        // Campo de Contraseña
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passPanel.add(new JLabel("Contraseña:"));
        password = new JPasswordField(15);
        passPanel.add(password);
        loginPanel.add(passPanel);

        // Selector de Rol
        JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rolPanel.add(new JLabel("Rol:"));
        rolBox = new JComboBox<>(new String[]{"Ganadero", "Inventariado", "Transportista", "Admin"});
        rolPanel.add(rolBox);
        loginPanel.add(rolPanel);

        // Botón Iniciar Sesión
        JPanel btnLoginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iniciarSesionButton = new JButton("Iniciar Sesión");
        btnLoginPanel.add(iniciarSesionButton);
        loginPanel.add(btnLoginPanel);

        // Botón Crear Cuenta
        JPanel btnRegisterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        crearCuentaButton = new JButton("Crear Cuenta");
        btnRegisterPanel.add(crearCuentaButton);
        loginPanel.add(btnRegisterPanel);

        // Etiqueta Recuperar Contraseña
        JPanel recoverPassPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        recuperarContraseñaLabel = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        recuperarContraseñaLabel.setForeground(Color.BLUE.darker());
        recuperarContraseñaLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        recoverPassPanel.add(recuperarContraseñaLabel);
        loginPanel.add(recoverPassPanel);

        // Añadir un panel vacío para empujar los elementos hacia arriba o para rellenar el espacio
        loginPanel.add(new JPanel()); // Esto es para que GridLayout tenga 8 filas y el espacio se distribuya un poco mejor.

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