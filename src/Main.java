import views.LoginView;
import controllers.LoginController;

/**
 * Clase principal de la aplicación.
 * Contiene el método main que inicia la interfaz de usuario.
 */
public class Main {
    /**
     * Método principal que se ejecuta al iniciar la aplicación.
     * Crea y muestra la ventana de inicio de sesión.
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginView loginView = new LoginView();
                LoginController loginController = new LoginController(loginView);
                loginView.setVisible(true);
            }
        });
    }
}