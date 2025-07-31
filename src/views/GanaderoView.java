package views;

import javax.swing.*;


public class GanaderoView extends JFrame {

    private JTable tableProductos;
    private JComboBox<String> comboEspecie;
    private JCheckBox alimentosCheckBox;
    private JCheckBox suplementosCheckBox;
    private JCheckBox blanceadosCheckBox;
    private JButton limipiarButton;
    private JButton añadirCarritoButton;
    private JButton verCarritoButton;
    private JTextField textBuscar;
    private JButton buscarButton;
    private JLabel lbDescripcion;
    private JPanel productos;

    public  GanaderoView(){
        setTitle("Transportista");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setContentPane(productos);

    }
    public JTable getTableProductos() {
        return tableProductos;
    }

    public JComboBox<String> getComboEspecie() {
        return comboEspecie;
    }

    public JCheckBox getAlimentosCheckBox() {
        return alimentosCheckBox;
    }

    public JCheckBox getSuplementosCheckBox() {
        return suplementosCheckBox;
    }

    public JCheckBox getBlanceadosCheckBox() {
        return blanceadosCheckBox;
    }

    public JButton getLimipiarButton() {
        return limipiarButton;
    }

    public JButton getAñadirCarritoButton() {
        return añadirCarritoButton;
    }

    public JButton getVerCarritoButton() {
        return verCarritoButton;
    }

    public JTextField getTextBuscar() {
        return textBuscar;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public JLabel getLbDescripcion() {
        return lbDescripcion;
    }

    public JPanel getProductos() {
        return productos;
    }
}
