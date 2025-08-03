package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GanaderoView extends JFrame {

    private JTable tableProductos;
    private JComboBox<String> comboEspecie;
    private JCheckBox alimentosCheckBox;
    private JCheckBox suplementosCheckBox;
    private JCheckBox blanceadosCheckBox;
    private JButton limpiarButton;
    private JButton añadirCarritoButton;
    private JButton verCarritoButton;
    private JButton volverButton;
    private JTextField textBuscar;
    private JButton buscarButton;
    private JLabel lbDescripcion;
    private JPanel productos;

    public GanaderoView() {
        setTitle("Ganadero - Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Etiqueta "Productos" (Título)
        JLabel lblProductosTitulo = new JLabel("Productos");
        lblProductosTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        productos.add(lblProductosTitulo, gbc);

        // "Opciones de Productos:"
        JLabel lblOpciones = new JLabel("Opciones de Productos:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        productos.add(lblOpciones, gbc);

        // "Especie:" y ComboBox
        JLabel lblEspecie = new JLabel("Especie:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        productos.add(lblEspecie, gbc);
        comboEspecie = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        productos.add(comboEspecie, gbc);
        gbc.gridwidth = 1; // Restablecer gridwidth

        // "Tipo de Producto:"
        JLabel lblTipoProducto = new JLabel("Tipo de Producto:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        productos.add(lblTipoProducto, gbc);

        // CheckBoxes para Tipo de Producto
        alimentosCheckBox = new JCheckBox("Alimentos");
        gbc.gridx = 0;
        gbc.gridy = 4;
        productos.add(alimentosCheckBox, gbc);

        suplementosCheckBox = new JCheckBox("Suplementos");
        gbc.gridx = 0;
        gbc.gridy = 5;
        productos.add(suplementosCheckBox, gbc);

        blanceadosCheckBox = new JCheckBox("Blanceados");
        gbc.gridx = 0;
        gbc.gridy = 6;
        productos.add(blanceadosCheckBox, gbc);

        // Campo de búsqueda y botón
        textBuscar = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0; // Para que el campo de texto ocupe más espacio horizontal
        productos.add(textBuscar, gbc);
        gbc.weightx = 0.0; // Restablecer weightx

        buscarButton = new JButton("Buscar");
        gbc.gridx = 2;
        gbc.gridy = 2;
        productos.add(buscarButton, gbc);

        // Label (podría ser para descripción)
        lbDescripcion = new JLabel(" "); // Inicialmente vacío
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = 3; // Ocupar espacio vertical para descripción
        gbc.fill = GridBagConstraints.BOTH; // Llenar el espacio disponible
        productos.add(lbDescripcion, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Restablecer fill
        gbc.gridheight = 1; // Restablecer gridheight

        // Botones de acción
        añadirCarritoButton = new JButton("Añadir Carrito");
        gbc.gridx = 3;
        gbc.gridy = 3;
        productos.add(añadirCarritoButton, gbc);

        verCarritoButton = new JButton("Ver Carrito");
        gbc.gridx = 3;
        gbc.gridy = 4;
        productos.add(verCarritoButton, gbc);

        limpiarButton = new JButton("Limpiar");
        gbc.gridx = 3;
        gbc.gridy = 5;
        productos.add(limpiarButton, gbc);

        // "Productos Disponibles:"
        JLabel lblProductosDisponibles = new JLabel("Productos Disponibles:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        productos.add(lblProductosDisponibles, gbc);

        // Tabla de productos
        tableProductos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableProductos);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.weighty = 1.0; // Permitir que la tabla crezca verticalmente
        gbc.fill = GridBagConstraints.BOTH;
        productos.add(scrollPane, gbc);
        gbc.weighty = 0.0; // Restablecer weighty
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1; // Restablecer gridwidth

        // Botón Volver
        volverButton = new JButton("Volver");
        gbc.gridx = 3;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        productos.add(volverButton, gbc);

        setContentPane(productos);
        pack();
        setLocationRelativeTo(null);
    }

    // Getters para todos los componentes
    public JTable getTableProductos() { return tableProductos; }
    public JComboBox<String> getComboEspecie() { return comboEspecie; }
    public JCheckBox getAlimentosCheckBox() { return alimentosCheckBox; }
    public JCheckBox getSuplementosCheckBox() { return suplementosCheckBox; }
    public JCheckBox getBlanceadosCheckBox() { return blanceadosCheckBox; }
    public JButton getLimpiarButton() { return limpiarButton; }
    public JButton getAñadirCarritoButton() { return añadirCarritoButton; }
    public JButton getVerCarritoButton() { return verCarritoButton; }
    public JButton getVolverButton() { return volverButton; }
    public JTextField getTextBuscar() { return textBuscar; }
    public JButton getBuscarButton() { return buscarButton; }
    public JLabel getLbDescripcion() { return lbDescripcion; }
    public JPanel getProductos() { return productos; }

    public void cargarDatosEnTabla(DefaultTableModel tableModel) {
        tableProductos.setModel(tableModel);
    }
}