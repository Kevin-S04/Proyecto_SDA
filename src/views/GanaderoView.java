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
    private JPanel productos; // El panel principal del diseñador

    public GanaderoView() {
        setTitle("Ganadero - Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(productos);

        pack(); // Ajusta el tamaño de la ventana al contenido
        setLocationRelativeTo(null); // Centra la ventana
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
    public JButton getVolverButton() { return volverButton; } // Nuevo getter
    public JTextField getTextBuscar() { return textBuscar; }
    public JButton getBuscarButton() { return buscarButton; }
    public JLabel getLbDescripcion() { return lbDescripcion; }
    public JPanel getProductos() { return productos; }

    public void cargarDatosEnTabla(DefaultTableModel tableModel) {
        tableProductos.setModel(tableModel);
    }
}