package views;

import models.Producto;
import models.Usuario;
import models.Producto;
import models.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class AdminView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JTable tbProducto;
    private JButton buscarButton;
    private JButton editarButton;
    private JButton nuevoButton;
    private JButton eliminarButton;
    private JTextField textBuscar;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboEspecie;
    private JPanel JpGestionar;
    private JTextField textid;
    private JTextField textPrecio;
    private JTextField textPresentacion;
    private JTextField textNombre;
    private JTextField textStock;
    private JTextField textDescripcion;

    // Usuarios
    private JTextField textIdU;
    private JTextField textCorreoU;
    private JTextField textNombreU;
    private JTable tbUsuarios;
    private JButton btnNuevoU;
    private JButton btnModificarU;
    private JButton btnLimpiarU;
    private JComboBox<String> comboRolU;

    private DefaultTableModel modelo;
    private DefaultTableModel modeloUsuarios;

    public AdminView() {
        setTitle("Panel de Administración");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(JpGestionar);

        inicializarTablaProductos();
        inicializarTablaUsuarios();
    }

    private void inicializarTablaProductos() {
        String[] columnas = {"ID", "Nombre", "Tipo", "Especie", "Precio", "Stock", "Presentación", "Descripción"};
        modelo = new DefaultTableModel(null, columnas);
        tbProducto.setModel(modelo);
    }

    private void inicializarTablaUsuarios() {
        String[] columnas = {"ID", "Nombre", "Correo", "Rol"};
        modeloUsuarios = new DefaultTableModel(null, columnas);
        tbUsuarios.setModel(modeloUsuarios);
    }

    public Producto obtenerProductoDelFormulario() {
        String id = textid.getText().trim();
        String nombre = textNombre.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();
        String especieTexto = (String) comboEspecie.getSelectedItem();
        List<String> especies = new ArrayList<>();
        if (especieTexto != null && !especieTexto.isEmpty()) {
            especies.add(especieTexto);
        }
        String presentacion = textPresentacion.getText().trim();
        String descripcion = textDescripcion.getText().trim();

        int stock;
        double precio;

        try {
            precio = Double.parseDouble(textPrecio.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio inválido");
            return null;
        }
        try {
            stock = Integer.parseInt(textStock.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock inválido");
            return null;
        }

        return new Producto(id, nombre, tipo, especies, precio, stock, presentacion, descripcion);
    }

    public Usuario obtenerUsuarioDelFormulario() {
        String id = textIdU.getText().trim();
        String nombre = textNombreU.getText().trim();
        String correo = textCorreoU.getText().trim();
        String rol = (String) comboRolU.getSelectedItem();

        if (nombre.isEmpty() || correo.isEmpty() || rol == null || rol.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos del usuario");
            return null;
        }
        return new Usuario(id, nombre, correo, rol);
    }

    public void limpiarFormularioUsuario() {
        textIdU.setText("");
        textNombreU.setText("");
        textCorreoU.setText("");
        comboRolU.setSelectedIndex(-1);
    }

    public void cargarTiposYEspecies(List<String> tipos, List<String> especies) {
        comboTipo.removeAllItems();
        for (String tipo : tipos) {
            comboTipo.addItem(tipo);
        }

        comboEspecie.removeAllItems();
        for (String especie : especies) {
            comboEspecie.addItem(especie);
        }
    }

    // Getters

    public JTabbedPane getTabbedPane1() { return tabbedPane1; }
    public JTable getTbProducto() { return tbProducto; }
    public JButton getBuscarButton() { return buscarButton; }
    public JButton getEditarButton() { return editarButton; }
    public JButton getNuevoButton() { return nuevoButton; }
    public JButton getEliminarButton() { return eliminarButton; }
    public JTextField getTextBuscar() { return textBuscar; }
    public JComboBox<String> getComboTipo() { return comboTipo; }
    public JComboBox<String> getComboEspecie() { return comboEspecie; }
    public JTextField getTextNombre() { return textNombre; }
    public JTextField getTextPrecio() { return textPrecio; }
    public JTextField getTextPresentacion() { return textPresentacion; }
    public JTextField getTextStock() { return textStock; }
    public JTextField getTextDescripcion() { return textDescripcion; }
    public DefaultTableModel getModeloProducto() { return modelo; }
    public DefaultTableModel getModeloUsuarios() { return modeloUsuarios; }

    // Usuarios getters
    public JTextField getTextIdU() { return textIdU; }
    public JTextField getTextCorreoU() { return textCorreoU; }
    public JTextField getTextNombreU() { return textNombreU; }
    public JTable getTbUsuarios() { return tbUsuarios; }
    public JButton getBtnNuevoU() { return btnNuevoU; }
    public JButton getBtnModificarU() { return btnModificarU; }
    public JButton getBtnLimpiarU() { return btnLimpiarU; }
    public JComboBox<String> getComboRolU() { return comboRolU; }
}