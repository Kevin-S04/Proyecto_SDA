package controllers;

import models.Pedido;
import models.Producto;
import models.ProductoPedido;
import models.Usuario;
import services.Pedido_Services;
import services.ProductServices;
import views.GanaderoView;
import views.LoginView;
import utils.Mensajes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GanaderoController implements ActionListener {

    private final GanaderoView ganaderoView;
    private final ProductServices productServices;
    private final Pedido_Services pedidoServices;
    private final DefaultTableModel tableModel;
    private List<Producto> todosLosProductos;
    private List<Producto> productosActuales;
    private final List<ProductoPedido> carrito;
    private final Usuario usuarioAutenticado;

    public GanaderoController(GanaderoView ganaderoView, Usuario usuarioAutenticado) {
        this.ganaderoView = ganaderoView;
        this.productServices = new ProductServices();
        this.pedidoServices = new Pedido_Services();
        this.tableModel = new DefaultTableModel();
        this.carrito = new ArrayList<>();
        this.usuarioAutenticado = usuarioAutenticado;

        configurarTabla();
        configurarListeners();
        cargarDatosIniciales();
    }

    private void configurarTabla() {
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Especies");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Presentación");
        ganaderoView.getTableProductos().setModel(tableModel);
    }

    private void configurarListeners() {
        ganaderoView.getComboEspecie().addActionListener(e -> actualizarProductos());
        ganaderoView.getAlimentosCheckBox().addItemListener(e -> actualizarProductos());
        ganaderoView.getSuplementosCheckBox().addItemListener(e -> actualizarProductos());
        ganaderoView.getBlanceadosCheckBox().addItemListener(e -> actualizarProductos());
        ganaderoView.getBuscarButton().addActionListener(e -> actualizarProductos());
        ganaderoView.getLimpiarButton().addActionListener(e -> limpiarFiltros());

        ganaderoView.getAñadirCarritoButton().addActionListener(this);
        ganaderoView.getVerCarritoButton().addActionListener(this);
        ganaderoView.getVolverButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ganaderoView.getAñadirCarritoButton()) {
            añadirAlCarrito();
        } else if (e.getSource() == ganaderoView.getVerCarritoButton()) {
            verCarrito();
        } else if (e.getSource() == ganaderoView.getVolverButton()) {
            volverALogin();
        }
    }

    private void cargarDatosIniciales() {
        // Cargar todos los productos una sola vez
        todosLosProductos = productServices.obtenerTodosProductos();

        // Cargar especies en el combo box
        ganaderoView.getComboEspecie().removeAllItems();
        ganaderoView.getComboEspecie().addItem(""); // Opción vacía para "todas"
        productServices.obtenerEspeciesDisponibles().forEach(esp ->
                ganaderoView.getComboEspecie().addItem(esp));

        // Cargar productos iniciales en la tabla
        actualizarProductos();
    }

    private void actualizarProductos() {
        String especieSeleccionada = (String) ganaderoView.getComboEspecie().getSelectedItem();
        String textoBusqueda = ganaderoView.getTextBuscar().getText().trim().toLowerCase();

        List<String> tiposSeleccionados = new ArrayList<>();
        if (ganaderoView.getAlimentosCheckBox().isSelected()) tiposSeleccionados.add("Alimento");
        if (ganaderoView.getSuplementosCheckBox().isSelected()) tiposSeleccionados.add("Suplemento");
        if (ganaderoView.getBlanceadosCheckBox().isSelected()) tiposSeleccionados.add("Balanceado");

        productosActuales = todosLosProductos.stream()
                // Filtro por texto en nombre y descripción
                .filter(p -> textoBusqueda.isEmpty() ||
                        p.getNombre().toLowerCase().contains(textoBusqueda) ||
                        p.getDescripcion().toLowerCase().contains(textoBusqueda))
                // Filtro por especie
                .filter(p -> especieSeleccionada == null || especieSeleccionada.isEmpty() ||
                        p.getEspecie().contains(especieSeleccionada))
                // Filtro por tipo
                .filter(p -> tiposSeleccionados.isEmpty() || tiposSeleccionados.contains(p.getTipo()))
                .collect(Collectors.toList());

        actualizarTabla();
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);

        for (Producto p : productosActuales) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getTipo(),
                    String.join(", ", p.getEspecie()),
                    String.format("$%.2f", p.getPrecio()),
                    p.getStock(),
                    p.getPresentacion()
            });
        }
    }

    private void limpiarFiltros() {
        ganaderoView.getComboEspecie().setSelectedIndex(0);
        ganaderoView.getAlimentosCheckBox().setSelected(false);
        ganaderoView.getSuplementosCheckBox().setSelected(false);
        ganaderoView.getBlanceadosCheckBox().setSelected(false);
        ganaderoView.getTextBuscar().setText("");
        actualizarProductos();
    }

    // --- MÉTODOS DEL CARRITO Y PEDIDO ---

    private void añadirAlCarrito() {
        int selectedRow = ganaderoView.getTableProductos().getSelectedRow();
        if (selectedRow == -1) {
            Mensajes.mostrarAdvertencia(ganaderoView, "Por favor, seleccione un producto para añadir al carrito.");
            return;
        }

        String idProducto = (String) ganaderoView.getTableProductos().getValueAt(selectedRow, 0);

        Producto productoSeleccionado = productosActuales.stream()
                .filter(p -> p.getId().equals(idProducto))
                .findFirst()
                .orElse(null);

        if (productoSeleccionado == null) {
            Mensajes.mostrarError(ganaderoView, "Error: Producto no encontrado en la lista actual.");
            return;
        }

        String cantidadStr = JOptionPane.showInputDialog(ganaderoView, "Ingrese la cantidad de " + productoSeleccionado.getNombre() + ":", "Añadir al Carrito", JOptionPane.QUESTION_MESSAGE);
        if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr.trim());
            if (cantidad <= 0) {
                Mensajes.mostrarAdvertencia(ganaderoView, "La cantidad debe ser mayor a cero.");
                return;
            }
            if (cantidad > productoSeleccionado.getStock()) {
                Mensajes.mostrarAdvertencia(ganaderoView, "No hay suficiente stock. Stock disponible: " + productoSeleccionado.getStock());
                return;
            }

            // Añadir o actualizar el producto en el carrito
            boolean productoYaEnCarrito = false;
            for (ProductoPedido item : carrito) {
                if (item.getIdProducto().equals(idProducto)) {
                    item.setCantidad(item.getCantidad() + cantidad);
                    productoYaEnCarrito = true;
                    break;
                }
            }
            if (!productoYaEnCarrito) {
                carrito.add(new ProductoPedido(productoSeleccionado.getId(), productoSeleccionado.getNombre(), cantidad, productoSeleccionado.getPrecio()));
            }

            Mensajes.mostrarInformacion(ganaderoView, cantidad + " unidades de " + productoSeleccionado.getNombre() + " añadidas al carrito.");
        } catch (NumberFormatException ex) {
            Mensajes.mostrarError(ganaderoView, "Cantidad inválida. Por favor, ingrese un número.");
        }
    }

    private void verCarrito() {
        if (carrito.isEmpty()) {
            Mensajes.mostrarInformacion(ganaderoView, "El carrito de compras está vacío.");
            return;
        }

        DefaultTableModel carritoModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        carritoModel.setColumnIdentifiers(new String[]{"Producto", "Cantidad", "Precio Unitario", "Subtotal"});
        double total = 0.0;

        for (ProductoPedido item : carrito) {
            double subtotal = item.getCantidad() * item.getPrecioUnitario();
            total += subtotal;
            carritoModel.addRow(new Object[]{item.getNombreProducto(), item.getCantidad(), item.getPrecioUnitario(), subtotal});
        }

        JTable carritoTable = new JTable(carritoModel);
        JScrollPane scrollPane = new JScrollPane(carritoTable);

        JLabel totalLabel = new JLabel("Total del Pedido: $" + String.format("%.2f", total));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(new JLabel("Contenido del Carrito:"), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(totalLabel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(ganaderoView, panel, "Carrito de Compras", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Lógica para realizar el pedido real
            Pedido nuevoPedido = new Pedido(
                    null,
                    usuarioAutenticado.getId(),
                    new Date(),
                    total,
                    carrito
            );

            boolean guardado = pedidoServices.guardarPedido(nuevoPedido);
            if (guardado) {
                Mensajes.mostrarInformacion(ganaderoView, "¡Pedido realizado con éxito!\nTotal: $" + String.format("%.2f", total));
                carrito.clear();
                actualizarProductos(); // Recargar productos para que el stock se vea actualizado
            } else {
                Mensajes.mostrarError(ganaderoView, "Hubo un error al realizar el pedido.");
            }
        }
    }

    private void volverALogin() {
        ganaderoView.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}