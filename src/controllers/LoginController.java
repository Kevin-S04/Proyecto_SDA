package controllers;

import models.Usuario;
import services.Users_Services;

import views.GanaderoView;
import views.InventarioView;
import views.LoginView;
import views.TransportistaView; // Importación añadida
import utils.Mensajes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.mongodb.MongoException;

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

                // Mostrar formulario segun el rol
                String rol = usuarioAutenticado.getRol();
                switch (rol) {
                    case "Admin":
                        System.out.println("En construcción");
                        break;
                    case "Ganadero":
                        GanaderoView gnv = new GanaderoView();
                        new GanaderoController(gnv, usuarioAutenticado);
                        gnv.setVisible(true);
                        break;
                    case "Inventariado":
                        InventarioView in = new InventarioView();
                        new InventarioController(in);
                        in.setVisible(true);
                        break;
                    case "Transportista":
                        TransportistaView tr = new TransportistaView(); // Corregido: "TransportistaView"
                        new TransporteController(tr); // Importación añadida por la clase del controlador
                        tr.setVisible(true);
                        break;
                    default:
                        // Si el rol no coincide con ninguna vista, se puede simplemente cerrar o mostrar un mensaje
                        System.out.println("Rol no reconocido o vista no implementada.");
                        System.exit(0);
                        break;
                }

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
        // ... (Tu código de registro, no modificado)
        JTextField nombreField = new JTextField(20);
        JTextField correoField = new JTextField(20);
        JPasswordField claveField = new JPasswordField(20);
        JPasswordField confirmarClaveField = new JPasswordField(20);
        JComboBox<String> rolBox = new JComboBox<>(new String[]{"Ganadero", "Inventariado", "Transportista", "Admin"}); // Incluí "Admin" por si acaso

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 filas, 2 columnas, 5px de espacio
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
                // Modificado para coincidir con la clase Usuario actualizada
                Usuario nuevoUsuario = new Usuario(null, nombre, correo, clave, rol);
                boolean registrado = usersServices.insertarUsuario(nuevoUsuario);
                if (registrado) {
                    Mensajes.mostrarInformacion(loginView, "Usuario registrado exitosamente.");
                } else {
                    Mensajes.mostrarError(loginView, "No se pudo registrar el usuario. El correo podría ya estar en uso o hubo un error de DB.");
                }
            } catch (MongoException e) {
                Mensajes.mostrarError(loginView, "Error al registrar usuario en la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Muestra un diálogo de JOptionPane para la recuperación de contraseña.
     * Pide al usuario su correo electrónico y una nueva contraseña.
     */
    private void abrirOpcionRecuperarContrasena() {
        // ... (Tu código de recuperación, no modificado)
        JTextField correoField = new JTextField(20);
        JPasswordField nuevaClaveField = new JPasswordField(20);
        JPasswordField confirmarNuevaClaveField = new JPasswordField(20);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); // 3 filas, 2 columnas, 5px de espacio
        panel.add(new JLabel("Correo Electrónico:"));
        panel.add(correoField);
        panel.add(new JLabel("Nueva Contraseña:"));
        panel.add(nuevaClaveField);
        panel.add(new JLabel("Confirmar Nueva Contraseña:"));
        panel.add(confirmarNuevaClaveField);

        int result = JOptionPane.showConfirmDialog(loginView, panel, "Recuperar/Restablecer Contraseña",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String correo = correoField.getText();
            String nuevaClave = new String(nuevaClaveField.getPassword());
            String confirmarNuevaClave = new String(confirmarNuevaClaveField.getPassword());

            if (correo.isEmpty() || nuevaClave.isEmpty() || confirmarNuevaClave.isEmpty()) {
                Mensajes.mostrarAdvertencia(loginView, "Todos los campos son obligatorios para restablecer la contraseña.");
                return;
            }
            if (!nuevaClave.equals(confirmarNuevaClave)) {
                Mensajes.mostrarError(loginView, "Las nuevas contraseñas no coinciden.");
                return;
            }

            try {
                boolean actualizado = usersServices.actualizarContrasena(correo, nuevaClave);
                if (actualizado) {
                    Mensajes.mostrarInformacion(loginView, "Contraseña actualizada exitosamente.");
                } else {
                    Mensajes.mostrarError(loginView, "No se pudo actualizar la contraseña. El correo electrónico no fue encontrado.");
                }
            } catch (MongoException e) {
                Mensajes.mostrarError(loginView, "Error al actualizar contraseña en la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}