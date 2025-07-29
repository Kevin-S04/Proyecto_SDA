package views;

import javax.swing.*;

public class LoginView extends JFrame{
    private JPanel loginPanel;
    private JTextField usuarioText;
    private JPasswordField password;
    private JButton iniciarSesionButton;
    private JButton crearCuentaButton;
    private JLabel recuperarContraseñaLabel;

    public LoginView(){
        setTitle("Login");
        setContentPane(loginPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo(null);
    }
}
