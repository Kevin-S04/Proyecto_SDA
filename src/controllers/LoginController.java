package controllers;

import models.Usuario;
import services.Users_Services;
import views.LoginView;
import utils.Mensajes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.mongodb.MongoException;
import javax.swing.SpringLayout;
import javax.swing.Spring;

/**
 * Clase LoginController que gestiona la lógica de negocio para la ventana de inicio de sesión (LoginView).
 * Se encarga de manejar las interacciones del usuario, validar los datos y comunicarse con los servicios
 * para la autenticación y el manejo de flujos como registro y recuperación de contraseña sin abrir nuevas ventanas JFrame/JDialog dedicadas.
 */
public class LoginController implements ActionListener {

    /** Instancia de la vista de login asociada a este controlador. */
    private LoginView loginView;
    /** Instancia del servicio de usuarios para interactuar con la base de datos. */
    private Users_Services usersServices;

    /**
     * Constructor de la clase LoginController.
     * Inicializa el controlador con la vista de login y configura los listeners para los componentes de la vista.
     * @param loginView La instancia de {@link views.LoginView} que este controlador gestionará.
     */
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.usersServices = new Users_Services();

        this.loginView.getIniciarSesionButton().addActionListener(this);
        this.loginView.getCrearCuentaButton().addActionListener(this);

        this.loginView.getRecuperarContraseñaLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirOpcionRecuperarContrasena();
            }
        });
    }

    /**
     * Método que se ejecuta cuando ocurre una acción en un componente (ej. clic de botón).
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginView.getIniciarSesionButton()) {
            iniciarSesion();
        } else if (e.getSource() == loginView.getCrearCuentaButton()) {
            abrirOpcionCrearCuenta();
        }
    }

    /**
     * Maneja la lógica de inicio de sesión.
     * Obtiene los datos del usuario de la vista, los valida y los envía al servicio de usuarios
     * para intentar la autenticación. Muestra mensajes al usuario según el resultado.
     */
    private void iniciarSesion() {
        String correo = loginView.getUsuarioText().getText();
        String clave = new String(loginView.getPassword().getPassword());
        String selectedRol = (String) loginView.getRolBox().getSelectedItem();

        if (correo.isEmpty() || clave.isEmpty() || selectedRol == null || selectedRol.isEmpty()) {
            Mensajes.mostrarError(loginView, "Por favor, complete todos los campos y seleccione un rol.");
            return;
        }

        try {
            Usuario usuarioAutenticado = usersServices.login(correo, clave, selectedRol);

            if (usuarioAutenticado != null) {
                Mensajes.mostrarInformacion(loginView, "¡Inicio de sesión exitoso como " + usuarioAutenticado.getRol() + "!");
                loginView.dispose();
                mostrarPantallaPrincipalPorRol(usuarioAutenticado.getRol());

            } else {
                Mensajes.mostrarError(loginView, "Correo, clave o rol incorrectos.");
            }
        } catch (MongoException e) {
            Mensajes.mostrarError(loginView, "Error de conexión/base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Mensajes.mostrarError(loginView, "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra un diálogo de JOptionPane para el registro de un nuevo usuario.
     * Permite al usuario ingresar los datos básicos y llama al servicio de usuarios.
     */
    private void abrirOpcionCrearCuenta() {
        JTextField nombreField = new JTextField();
        JTextField correoField = new JTextField();
        JPasswordField claveField = new JPasswordField();
        JPasswordField confirmarClaveField = new JPasswordField();
        JComboBox<String> rolBox = new JComboBox<>(new String[]{"Ganadero", "Inventariado", "Transportista", "Admin"});

        JPanel panel = new JPanel(new SpringLayout());
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Correo:"));
        panel.add(correoField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(claveField);
        panel.add(new JLabel("Confirmar Contraseña:"));
        panel.add(confirmarClaveField);
        panel.add(new JLabel("Rol:"));
        panel.add(rolBox);

        SpringUtilities.makeCompactGrid(panel,
                5, 2,
                6, 6,
                6, 6);

        int result = JOptionPane.showConfirmDialog(loginView, panel, "Registrar Nuevo Usuario",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            String correo = correoField.getText();
            String clave = new String(claveField.getPassword());
            String confirmarClave = new String(confirmarClaveField.getPassword());
            String rol = (String) rolBox.getSelectedItem();

            if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || confirmarClave.isEmpty() || rol == null || rol.isEmpty()) {
                Mensajes.mostrarAdvertencia(loginView, "Todos los campos son obligatorios.");
                return;
            }
            if (!clave.equals(confirmarClave)) {
                Mensajes.mostrarError(loginView, "Las contraseñas no coinciden.");
                return;
            }

            try {
                Usuario nuevoUsuario = new Usuario(nombre, correo, clave, rol);
                boolean registrado = usersServices.insertarUsuario(nuevoUsuario);
                if (registrado) {
                    Mensajes.mostrarInformacion(loginView, "Usuario registrado exitosamente.");
                } else {
                    Mensajes.mostrarError(loginView, "No se pudo registrar el usuario. El correo podría ya estar en uso.");
                }
            } catch (MongoException e) {
                Mensajes.mostrarError(loginView, "Error al registrar usuario en la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Muestra un diálogo de JOptionPane para la recuperación de contraseña.
     * Pide al usuario su correo electrónico.
     */
    private void abrirOpcionRecuperarContrasena() {
        String correo = JOptionPane.showInputDialog(loginView, "Ingrese su correo electrónico para recuperar la contraseña:", "Recuperar Contraseña", JOptionPane.QUESTION_MESSAGE);

        if (correo != null && !correo.trim().isEmpty()) {
            Mensajes.mostrarInformacion(loginView, "Se ha solicitado la recuperación de contraseña para: " + correo + "\n(Funcionalidad no implementada completamente)");
        } else if (correo != null) {
            Mensajes.mostrarAdvertencia(loginView, "Debe ingresar un correo electrónico.");
        }
    }

    /**
     * Simula la apertura de una pantalla principal diferente según el rol del usuario,
     * mostrando un mensaje informativo con JOptionPane y cerrando la aplicación.
     * @param rol El rol del usuario autenticado.
     */
    private void mostrarPantallaPrincipalPorRol(String rol) {
        String mensaje = "¡Bienvenido, " + rol + "!\nHas accedido a tu panel principal.";
        Mensajes.mostrarInformacion(null, mensaje);
        System.exit(0);
    }

    /**
     * Clase interna auxiliar para SpringLayout.
     */
    private static class SpringUtilities {
        /**
         * A la luz de un JPanel que usa SpringLayout, establece los componentes en un diseño de cuadrícula.
         * @param parent El panel que usa SpringLayout.
         * @param rows Número de filas.
         * @param cols Número de columnas.
         * @param initialX Distancia del lado izquierdo del primer componente.
         * @param initialY Distancia de la parte superior del primer componente.
         * @param xPad Acolchado horizontal entre componentes.
         * @param yPad Acolchado vertical entre componentes.
         */
        public static void makeCompactGrid(Container parent,
                                           int rows, int cols,
                                           int initialX, int initialY,
                                           int xPad, int yPad) {
            SpringLayout layout;
            try {
                layout = (SpringLayout)parent.getLayout();
            } catch (ClassCastException exc) {
                System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
                return;
            }

            Spring x = Spring.constant(initialX);
            for (int c = 0; c < cols; c++) {
                Spring width = Spring.constant(0);
                for (int r = 0; r < rows; r++) {
                    width = Spring.max(width,
                            layout.getConstraints(parent.getComponent(r * cols + c)).
                                    getWidth());
                }
                for (int r = 0; r < rows; r++) {
                    SpringLayout.Constraints constraints = layout.getConstraints(
                            parent.getComponent(r * cols + c));
                    constraints.setX(x);
                    constraints.setWidth(width);
                }
                x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
            }

            Spring y = Spring.constant(initialY);
            for (int r = 0; r < rows; r++) {
                Spring height = Spring.constant(0);
                for (int c = 0; c < cols; c++) {
                    height = Spring.max(height,
                            layout.getConstraints(parent.getComponent(r * cols + c)).
                                    getHeight());
                }
                for (int c = 0; c < cols; c++) {
                    SpringLayout.Constraints constraints = layout.getConstraints(
                            parent.getComponent(r * cols + c));
                    constraints.setY(y);
                    constraints.setHeight(height);
                }
                y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
            }

            SpringLayout.Constraints pCons = layout.getConstraints(parent);
            pCons.setConstraint(SpringLayout.EAST, x);
            pCons.setConstraint(SpringLayout.SOUTH, y);
        }
    }
}