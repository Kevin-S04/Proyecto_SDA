package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventarioView extends JFrame {

    private JPanel inventarioPanel;
    private JTable pedidosTable;
    private JButton marcarEnviadoButton;
    private JButton volverButton;
    private JScrollPane scrollPane;

    public InventarioView() {
        setTitle("Inventario - Gestión de Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        inventarioPanel = new JPanel(new BorderLayout());

        pedidosTable = new JTable();
        scrollPane = new JScrollPane(pedidosTable);
        inventarioPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        marcarEnviadoButton = new JButton("Marcar como Enviado");
        volverButton = new JButton("Volver");
        buttonPanel.add(marcarEnviadoButton);
        buttonPanel.add(volverButton);
        inventarioPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(inventarioPanel);
    }

    public JTable getPedidosTable() { return pedidosTable; }
    public JButton getMarcarEnviadoButton() { return marcarEnviadoButton; }
    public JButton getVolverButton() { return volverButton; }

    public void cargarDatosEnTabla(DefaultTableModel tableModel) {
        pedidosTable.setModel(tableModel);
    }
}