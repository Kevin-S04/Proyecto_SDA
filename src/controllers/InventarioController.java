package controllers;

import models.Inventario;
import models.ProductoPedido;
import services.Inventario_Services;
import views.InventarioView;
import views.LoginView;
import utils.Mensajes;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class InventarioController implements ActionListener {

    private final InventarioView view;
    private final Inventario_Services service;
    private List<Inventario> pedidosPendientes;

    public InventarioController(InventarioView view) {
        this.view = view;
        this.service = new Inventario_Services();

        this.view.getMarcarEnviadoButton().addActionListener(this);
        this.view.getVolverButton().addActionListener(this);

        cargarPedidosPendientes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getMarcarEnviadoButton()) {
            marcarComoEnviado();
        } else if (e.getSource() == view.getVolverButton()) {
            volverALogin();
        }
    }

    private void cargarPedidosPendientes() {
        pedidosPendientes = service.obtenerPedidosEnBodega();

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.setColumnIdentifiers(new String[]{"ID Pedido", "Fecha", "Productos"});

        for (Inventario pedido : pedidosPendientes) {
            String productosStr = pedido.getProductos().stream()
                    .map(p -> p.getNombreProducto() + " (" + p.getCantidad() + ")")
                    .collect(Collectors.joining(", "));

            tableModel.addRow(new Object[]{
                    pedido.getIdPedido(),
                    pedido.getFecha(),
                    productosStr
            });
        }

        view.cargarDatosEnTabla(tableModel);
    }

    private void marcarComoEnviado() {
        int selectedRow = view.getPedidosTable().getSelectedRow();
        if (selectedRow == -1) {
            Mensajes.mostrarAdvertencia(view, "Por favor, seleccione un pedido de la tabla.");
            return;
        }

        String idPedido = (String) view.getPedidosTable().getValueAt(selectedRow, 0);

        boolean exito = service.procesarEnvio(idPedido);

        if (exito) {
            Mensajes.mostrarInformacion(view, "Pedido marcado como enviado y stock actualizado.");
            cargarPedidosPendientes(); // Recargar la tabla para ver los cambios
        } else {
            Mensajes.mostrarError(view, "Hubo un error al procesar el pedido.");
        }
    }

    private void volverALogin() {
        view.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}