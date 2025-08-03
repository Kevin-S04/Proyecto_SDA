package controllers;

import services.Transporte_Services;
import views.TransportistaView;
import views.LoginView;
import utils.Mensajes;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la vista del Transportista.
 * Gestiona la visualización de pedidos enviados y su marcaje como entregados.
 */
public class TransporteController implements ActionListener {

    private final TransportistaView view;
    private final Transporte_Services service;

    /**
     * Constructor del controlador de transporte.
     * @param view La vista del transportista.
     */
    public TransporteController(TransportistaView view) {
        this.view = view;
        this.service = new Transporte_Services();

        // Asignar listeners a los botones
        this.view.getVolverButton().addActionListener(this);
        this.view.getEntregadoButton().addActionListener(this);

        cargarDatosDePedidos();
    }

    /**
     * Maneja los eventos de acción de los botones de la vista.
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getVolverButton()) {
            volverALogin();
        } else if (e.getSource() == view.getEntregadoButton()) {
            marcarComoEntregado();
        }
    }

    /**
     * Vuelve a la pantalla de login.
     */
    private void volverALogin() {
        view.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }

    /**
     * Marca el pedido seleccionado en la tabla como "Entregado" en la base de datos.
     */
    private void marcarComoEntregado() {
        int selectedRow = view.getPedidosTable().getSelectedRow();
        if (selectedRow != -1) {
            String idPedido = (String) view.getPedidosTable().getValueAt(selectedRow, 0);

            if (service.marcarComoEntregado(idPedido)) {
                Mensajes.mostrarInformacion(view, "El pedido " + idPedido + " ha sido marcado como 'Entregado'.");
                cargarDatosDePedidos();
            } else {
                Mensajes.mostrarError(view, "No se pudo actualizar el estado del pedido " + idPedido + ".");
            }
        } else {
            Mensajes.mostrarAdvertencia(view, "Por favor, seleccione un pedido de la tabla.");
        }
    }

    /**
     * Obtiene los IDs de los pedidos en estado "Enviado" del servicio y los carga en la tabla de la vista.
     */
    public void cargarDatosDePedidos() {
        List<String> pedidosEnviados = service.obtenerPedidosEnviados();
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.setColumnIdentifiers(new String[]{"ID Pedido"});

        for (String idPedido : pedidosEnviados) {
            tableModel.addRow(new Object[]{idPedido});
        }

        view.cargarDatosEnTabla(tableModel);
    }
}