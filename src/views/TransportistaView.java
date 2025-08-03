package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Vista para el módulo de Transporte.
 * Muestra una tabla con los pedidos listos para ser entregados y un botón para marcarlos como entregados.
 */
public class TransportistaView extends JFrame {

    private JPanel transportistaPanel;
    private JTable pedidosTable;
    private JButton entregadoButton;
    private JButton volverButton;
    private JScrollPane scrollPane;

    public TransportistaView() {
        setTitle("Transportista - Pedidos para entrega");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        transportistaPanel = new JPanel(new BorderLayout());

        pedidosTable = new JTable();
        scrollPane = new JScrollPane(pedidosTable);
        transportistaPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        entregadoButton = new JButton("Marcar como Entregado");
        volverButton = new JButton("Volver");
        buttonPanel.add(entregadoButton);
        buttonPanel.add(volverButton);
        transportistaPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(transportistaPanel);
    }

    public JTable getPedidosTable() {
        return pedidosTable;
    }

    public JButton getEntregadoButton() {
        return entregadoButton;
    }

    public JButton getVolverButton() {
        return volverButton;
    }

    public void cargarDatosEnTabla(DefaultTableModel tableModel) {
        pedidosTable.setModel(tableModel);
    }
}